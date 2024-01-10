const formatter = new Intl.NumberFormat("vi-VI", {
    style: "currency",
    currency: "VND",

    // These options are needed to round to whole numbers if that's what you want.
    //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
    //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
});

export function toCurrency(currency, hasUnit = true) {
    if (currency === null || currency === undefined) return "";
    if (hasUnit) return formatter.format(currency);
    return formatter.format(currency).replace(/[^0-9.,]/g, "");
}

export function reverseCurrency(currency) {
    return Number(currency.replace(/[^0-9]/g, ""));
}

export function repairCurrency(currency, hasUnit = true) {
    return toCurrency(reverseCurrency(currency), hasUnit);
}
