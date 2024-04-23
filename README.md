#### Portfolio Analytics Engine ####

The following code to creates a simple engine that mimics live prices streamed from a datasource and valuate a portfolio.

Supported assets: **Stocks**, **European Options**

Domain Structure:
- **Security**: Ticker (Abstract class, can have different implementations of a Security).
- **Asset**: Security + Quantity 
- **Price**: Security + Value + Time
- **Portfolio**: Collection of Assets
- **Valuation Service**: Valuates a Portfolio, given a Price Publisher

Database Tables:
**Security**
| ticker    | securityType |
| -------- | ------- |
| AAPL  | 1   |
| AAPL-DEC-2024-123-C |   2   |

**SecurityType**
| value    | name |
| -------- | ------- |
|  1 | STOCK   |
|  2 |   OPTION   |

**Option**
| ticker    | underlier | strikePrice | maturityDate | optionType | 
| -------- | ------- | -------- | ------- | ------- |
|  AAPL-DEC-2024-123-C | AAPL  | 123 | 2024-12-31 | CALL |


Flow:
1. Creates a portfolio by reading assets from CSV and querying db for security information
2. Initialize MockMarketDataProvider
3. Create ValuationService for given portfolio and register ValuationService as a subscriber to MockMarketDataService
4. As MockMarketDataProvder publishes updates, ValuationService revaluates portfolio and prints results.

Key Features:
- MockMarketDataProvider: Attempts to generate prices for a given list of securities provied using Discrete-time Geometric Brownian Motion
  - Initialized by providing it a list of securities to generate price for
  - First publish will provide a price for all securities, subsequent publishes are based on chance.
- OptionPriceCalculator:
  - Uses standard Black-Scholes model for pricing based on the underlier price, strike, volatility etc.
  - Standard Normal CDF is approxiamted based on https://www.hrpub.org/download/20181230/MS1-13412146.pdf
