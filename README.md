#### Portfolio Analytics Engine ####

The following code to creates a simple engine that mimics live prices streamed from a datasource and valuate a portfolio.

Supported assets: **Stocks**, **European Options**

Domain Structure:
- **Security**: Ticker + Description
- **Asset**: Security + Quantity --> Can have different implementations of an Asset.
- **Price**: Security + Value + Time
- **Portfolio**: Collection of Assets
- **Valuation Service**: Valuates a Portfolio, given a Price Publisher

Key Features:
- MockMarketDataProvider: Attempts to generate prices for a given list of securities provied using Discrete-time Geometric Brownian Motion
  - Initialized by providing it a list of securities to generate price for
  - First publish will provide a price for all securities, subsequent publishes are based on chance.
- OptionPriceCalculator:
  - Uses standard Black-Scholes model for pricing based on the underlier price, strike, volatility etc.
  - Standard Normal CDF is approxiamted based on https://www.hrpub.org/download/20181230/MS1-13412146.pdf
