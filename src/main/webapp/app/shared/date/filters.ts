import Vue from 'vue';
import { format, parseISO } from 'date-fns';

export const DATE_FORMAT = 'dd.MM.yyyy';
export const DATE_TIME_FORMAT = 'yyyy-MM-dd HH:mm';

export const DATE_TIME_LONG_FORMAT = "yyyy-MM-dd'T'HH:mm";
export const INSTANT_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
export const ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXXXX";

export function initFilters() {
  Vue.filter('formatDate', value => {
    if (value) {
      return format(parseISO(value), DATE_TIME_FORMAT);      
    }
    return '';
  });
  Vue.filter('formatMillis', value => {
    if (value) {
      return format(new Date(value), DATE_TIME_FORMAT);
    }
    return '';
  });
}
