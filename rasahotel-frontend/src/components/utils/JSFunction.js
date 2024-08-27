import { createNumberMask } from "text-mask-addons";

export const formattedNumber = (number)=> new Intl.NumberFormat('id-ID', {
  style: 'currency',
  currency: 'IDR'
}).format(number);

  const defaultMaskOptions = {
    prefix: "",
    suffix: "",
    includeThousandsSeparator: true,
    thousandsSeparatorSymbol: ",",
    allowDecimal: true,
    decimalSymbol: ".",
    decimalLimit: 2, // how many digits allowed after the decimal
    integerLimit: 7, // limit length of integer numbers
    allowNegative: false,
    allowLeadingZeroes: false,
  };

export const currencyMask = createNumberMask(defaultMaskOptions);