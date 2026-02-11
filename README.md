# WaehrungsKursTool

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A modern Java library for fetching and managing currency exchange rates using the [Open Exchange Rates API](https://openexchangerates.org/).

## Features

- 🌍 **Real-time Exchange Rates**: Fetch current currency exchange rates from Open Exchange Rates API
- 💱 **Multi-Currency Support**: Supports 170+ currencies based on ISO 4217
- 🚀 **Modern Java**: Built with Java 17+ using modern APIs (HttpClient, Stream API)
- ⚡ **Lightweight**: Minimal dependencies (only org.json)
- 🔒 **Secure**: API key externalization via environment variables
- ✅ **Well-tested**: Comprehensive JUnit 5 test coverage

## Requirements

- Java 17 or higher
- Maven 3.6+
- Open Exchange Rates API key (free tier available)

## Installation

### Maven

Add this dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>rentasad.library</groupId>
    <artifactId>rentasad.library.basicTools.waehrungskursTool</artifactId>
    <version>4.0.1</version>
</dependency>
```

### Build from Source

```bash
git clone https://github.com/rentasad/rentasad.library.basicTools.waehrungskursTool.git
cd rentasad.library.basicTools.waehrungskursTool
mvn clean install
```

## Configuration

Set your Open Exchange Rates API key as an environment variable:

```bash
export OPEN_EXCHANGE_RATES_API_KEY="your_api_key_here"
```

**Note**: If no environment variable is set, the library will fall back to a default key (not recommended for production).

Get your free API key at [https://openexchangerates.org/signup/free](https://openexchangerates.org/signup/free)

## Usage

### Fetch Current Exchange Rates

```java
import rentasad.library.tools.waehrungskursTool.*;

// Fetch JSON data from API
String jsonData = WaehrungsKursTool.getJsonStringFromOpenExchangeRates();

// Parse into WaehrungskursItem object
WaehrungskursItem ratesItem = WaehrungsKursTool.getWaehrungsKursItemFromOpenExchangeRates(jsonData);

// Get base currency and timestamp
System.out.println("Base Currency: " + ratesItem.getBaseCurrencyEnum());
System.out.println("Last Updated: " + ratesItem.getTimeStamp());
```

### Get Specific Exchange Rate

```java
// Get EUR to USD rate
Double eurRate = ratesItem.getRatesMap().get(WaehrungenEnum.EUR);
System.out.println("EUR Rate: " + eurRate);

// Get GBP to USD rate
Double gbpRate = ratesItem.getRatesMap().get(WaehrungenEnum.GBP);
System.out.println("GBP Rate: " + gbpRate);
```

### Convert Currency Amounts

```java
// Convert 100 USD to EUR
Double usdToEurRate = ratesItem.getRatesMap().get(WaehrungenEnum.EUR);
Double amountInEur = 100.0 * usdToEurRate;
System.out.printf("100 USD = %.2f EUR%n", amountInEur);
```

### Iterate Over All Currencies

```java
// Print all available rates
for (WaehrungenEnum currency : WaehrungenEnum.values()) {
    if (ratesItem.getRatesMap().containsKey(currency)) {
        Double rate = ratesItem.getRatesMap().get(currency);
        System.out.printf("%s: %.4f%n", currency.name(), rate);
    }
}
```

## Project Structure

### Core Classes

#### `WaehrungsKursTool`
Main utility class for fetching and parsing exchange rate data.

**Methods**:
- `readUrl(String urlString)`: Fetches content from a URL using modern HttpClient
- `getJsonStringFromOpenExchangeRates()`: Retrieves JSON data from Open Exchange Rates API
- `getWaehrungsKursItemFromOpenExchangeRates(String jsonString)`: Parses JSON into WaehrungskursItem object

#### `WaehrungskursItem`
Represents a snapshot of exchange rates at a specific point in time.

**Properties**:
- `Date timeStamp`: When the rates were retrieved
- `WaehrungenEnum baseCurrencyEnum`: Base currency (usually USD)
- `HashMap<WaehrungenEnum, Double> ratesMap`: Map of currency codes to exchange rates

#### `WaehrungenEnum`
Enumeration of 170+ currencies based on ISO 4217 standard (USD, EUR, GBP, JPY, etc.)

## Error Handling

The library provides robust error handling:

```java
try {
    String jsonData = WaehrungsKursTool.getJsonStringFromOpenExchangeRates();
    WaehrungskursItem rates = WaehrungsKursTool.getWaehrungsKursItemFromOpenExchangeRates(jsonData);
} catch (IOException e) {
    System.err.println("Network error: " + e.getMessage());
} catch (InterruptedException e) {
    System.err.println("Request interrupted: " + e.getMessage());
} catch (IllegalArgumentException e) {
    System.err.println("Invalid data: " + e.getMessage());
}
```

## Testing

Run the test suite:

```bash
mvn test
```

The project includes comprehensive unit tests covering:
- Valid JSON parsing
- Missing rates handling
- Invalid currency codes
- Malformed JSON handling

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Exchange rate data provided by [Open Exchange Rates](https://openexchangerates.org/)
- Currency codes based on [ISO 4217](https://www.iso.org/iso-4217-currency-codes.html)

## Author

**Matthias Staud** - [Rentasad](https://github.com/rentasad)

Originally created for Gustini GmbH (2015)

## Changelog

### Version 4.0.1
- Migrated to Java 17+
- Replaced legacy `URL.openStream()` with modern `HttpClient`
- Externalized API key configuration
- Added comprehensive error handling
- Implemented Stream API for better performance
- Added timeout configuration
- Improved code quality and maintainability

---

For issues and feature requests, please use the [GitHub issue tracker](https://github.com/rentasad/rentasad.library.basicTools.waehrungskursTool/issues).

