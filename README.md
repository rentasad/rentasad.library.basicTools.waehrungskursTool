# WaehrungsKursTool

## Overview
`WaehrungsKursTool` is a Java project that provides tools for working with currency exchange rates. It includes classes for fetching, storing, and managing currency exchange rate data. The project is useful for developers who need to integrate exchange rate data into their applications, whether for e-commerce, financial analysis, or general currency conversion purposes.

## Project Components
The project consists of the following components:

### 1. WaehrungsKursTool
The main class for interacting with exchange rate data. It is responsible for fetching and processing the exchange rates from online sources, managing the data, and providing utility functions to use the rates.

### 2. WaehrungskursItem
Represents an exchange rate entry, containing details such as the base currency, timestamp, and rates for other currencies. It stores exchange rates in a `HashMap` and provides easy access to different rates.

### 3. WaehrungenEnum
An enumeration that represents various currencies. It defines different currency codes, such as USD, EUR, GBP, and many others, based on ISO 4217.

## Features
- **Fetch Exchange Rates**: Retrieve current exchange rates from an online source.
- **Store and Manage Exchange Rates**: Store rates with a timestamp and base currency for future reference.
- **Currency Enumeration**: Provides an enumeration of different currency codes for easy identification.

## Installation
To use `WaehrungsKursTool`, include the source files in your Java project. The classes depend on some common Java libraries such as `java.math.BigDecimal`, `java.net.URL`, and JSON parsing (e.g., `org.json`). Ensure these dependencies are available in your project.

## Usage
Here are some examples of how you can use the `WaehrungsKursTool` project:

### Fetch Exchange Rates
```java
WaehrungsKursTool tool = new WaehrungsKursTool();
tool.fetchExchangeRates();
```
This code snippet initializes the `WaehrungsKursTool` class and fetches the latest exchange rates from the configured source.

### Get Exchange Rate for a Specific Currency
```java
WaehrungskursItem item = tool.getExchangeRate();
Double rate = item.getRate(WaehrungenEnum.USD);
System.out.println("Exchange rate for USD: " + rate);
```
This snippet retrieves the exchange rate for USD using the `WaehrungskursItem` class.

### Enumerate Currencies
```java
for (WaehrungenEnum currency : WaehrungenEnum.values()) {
    System.out.println(currency);
}
```
This snippet prints all available currencies defined in `WaehrungenEnum`.

## Classes Summary
1. **`WaehrungsKursTool`**: Handles fetching and managing currency exchange rates.
    - **Methods**: `fetchExchangeRates()`, `getExchangeRate()`

2. **`WaehrungskursItem`**: Represents an individual exchange rate entry with methods to get specific rates.
    - **Properties**: `Date timeStamp`, `WaehrungenEnum baseCurrencyEnum`, `HashMap<WaehrungenEnum, Double> ratesMap`

3. **`WaehrungenEnum`**: An enumeration of currency codes based on ISO 4217.

## Contributing
Feel free to contribute to the `WaehrungsKursTool` project by forking the repository, making changes, and submitting a pull request. Bug fixes, new features, and optimizations are always appreciated.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

