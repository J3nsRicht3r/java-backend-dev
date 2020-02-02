package net.cryptic_game.backend.base;

import net.cryptic_game.backend.base.api.ApiHandler;
import net.cryptic_game.backend.base.config.BaseConfig;
import net.cryptic_game.backend.base.config.Config;
import net.cryptic_game.backend.base.config.DefaultConfig;
import net.cryptic_game.backend.base.data.session.Session;
import net.cryptic_game.backend.base.data.user.User;
import net.cryptic_game.backend.base.sql.SQLConnection;
import net.cryptic_game.backend.base.sql.SQLServer;
import net.cryptic_game.backend.base.sql.SQLServerType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public abstract class AppBootstrap {

    private static final Logger log = LoggerFactory.getLogger(AppBootstrap.class);
    private static AppBootstrap instance;

    protected final Config config;
    protected ApiHandler apiHandler;
    protected final SQLConnection sqlConnection;

    public AppBootstrap(final DefaultConfig config) {
        AppBootstrap.instance = this;
        this.config = new Config(config);
        this.setLoglevel(Level.valueOf(this.config.getAsString(BaseConfig.LOG_LEVEL)));

        this.initApi();
        this.sqlConnection = new SQLConnection();

        try {
            this.initSQLTableModels();
        } catch (SQLException e) {
            log.error("Can't register all table models.", e);
        }
        this.setUpSQL();
        this.init();
        this.start();
    }

    private void initSQLTableModels() throws SQLException {
        this.sqlConnection.addEntity(User.class);
        this.sqlConnection.addEntity(Session.class);
    }

    protected abstract void init();
    protected abstract void start();
    protected abstract void initApi();

    protected void setUpSQL() {
        this.sqlConnection.init(new SQLServer(
                this.config.getAsString(BaseConfig.SQL_SERVER_HOSTNAME),
                this.config.getAsInt(BaseConfig.SQL_SERVER_PORT),
                this.config.getAsString(BaseConfig.SQL_SERVER_DATABASE),
                this.config.getAsString(BaseConfig.SQL_SERVER_USERNAME),
                this.config.getAsString(BaseConfig.SQL_SERVER_PASSWORD),
                SQLServerType.getServer(this.config.getAsString(BaseConfig.SQL_SERVER_TYPE))
        ), !this.config.getAsBoolean(BaseConfig.PRODUCTIVE));
    }

    private void setLoglevel(final Level level) {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final LoggerConfig loggerConfig = ctx.getConfiguration().getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(level);
        ctx.updateLoggers();
    }

    public Config getConfig() {
        return this.config;
    }

    public ApiHandler getApiHandler() {
        return apiHandler;
    }

    public SQLConnection getSqlConnection() {
        return this.sqlConnection;
    }

    public static AppBootstrap getInstance() {
        return instance;
    }
}
