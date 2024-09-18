-- schema.sql

-- Users table
CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    first_name NVARCHAR(50),
    last_name NVARCHAR(50),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);

-- Market Data table
CREATE TABLE market_data (
    id INT IDENTITY(1,1) PRIMARY KEY,
    symbol NVARCHAR(20) NOT NULL,
    company_name NVARCHAR(100),
    current_price DECIMAL(18, 4) NOT NULL,
    change_amount DECIMAL(18, 4) NOT NULL,
    change_percent DECIMAL(8, 4) NOT NULL,
    volume INT NOT NULL,
    market_cap BIGINT,
    pe_ratio DECIMAL(10, 2),
    dividend_yield DECIMAL(5, 2),
    last_updated DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_market_data_symbol UNIQUE (symbol)
);

-- User Portfolios table
CREATE TABLE user_portfolios (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    symbol NVARCHAR(20) NOT NULL,
    quantity DECIMAL(18, 4) NOT NULL,
    average_purchase_price DECIMAL(18, 4) NOT NULL,
    purchase_date DATE NOT NULL,
    CONSTRAINT FK_user_portfolios_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT FK_user_portfolios_market_data FOREIGN KEY (symbol) REFERENCES market_data(symbol)
);

-- Watchlists table
CREATE TABLE watchlists (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    name NVARCHAR(50) NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_watchlists_users FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Watchlist Items table
CREATE TABLE watchlist_items (
    id INT IDENTITY(1,1) PRIMARY KEY,
    watchlist_id INT NOT NULL,
    symbol NVARCHAR(20) NOT NULL,
    added_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_watchlist_items_watchlists FOREIGN KEY (watchlist_id) REFERENCES watchlists(id),
    CONSTRAINT FK_watchlist_items_market_data FOREIGN KEY (symbol) REFERENCES market_data(symbol)
);

-- Alerts table
CREATE TABLE alerts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    symbol NVARCHAR(20) NOT NULL,
    alert_type NVARCHAR(20) NOT NULL, -- e.g., 'price_above', 'price_below', 'percent_change'
    threshold_value DECIMAL(18, 4) NOT NULL,
    is_active BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    last_triggered DATETIME2,
    CONSTRAINT FK_alerts_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT FK_alerts_market_data FOREIGN KEY (symbol) REFERENCES market_data(symbol)
);

-- User Settings table
CREATE TABLE user_settings (
    user_id INT PRIMARY KEY,
    theme NVARCHAR(20) DEFAULT 'light',
    notification_preferences NVARCHAR(MAX), -- JSON string to store notification preferences
    default_currency NVARCHAR(3) DEFAULT 'USD',
    CONSTRAINT FK_user_settings_users FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Indices
CREATE INDEX IX_market_data_symbol ON market_data (symbol);
CREATE INDEX IX_user_portfolios_user_id ON user_portfolios (user_id);
CREATE INDEX IX_user_portfolios_symbol ON user_portfolios (symbol);
CREATE INDEX IX_watchlists_user_id ON watchlists (user_id);
CREATE INDEX IX_watchlist_items_watchlist_id ON watchlist_items (watchlist_id);
CREATE INDEX IX_watchlist_items_symbol ON watchlist_items (symbol);
CREATE INDEX IX_alerts_user_id ON alerts (user_id);
CREATE INDEX IX_alerts_symbol ON alerts (symbol);