
public class rrg {
    // Исходные данные из задания
    private static final double[] xValues = {10, 15, 20, 25, 30}; // Значения x
    private static final double[] yValuesGiven = {3, 7, 11, 17, 18}; // Данные y из задачи
    private static final double h = xValues[1] - xValues[0]; // Шаг между узлами (равностоящие узлы)

    public static void main(String[] args) {
        // 1. Вывод таблицы с данными из задания
        System.out.println("Таблица с данными из задания:");
        printTable(xValues, yValuesGiven); // Вывод таблицы с исходными данными

        // 2. Интерполяция для данных из задания
        System.out.println("\nИнтерполяция для данных из задания:");
        interpolate(xValues, yValuesGiven, 27); // Интерполяция для значения x = 27

        // 3. Расчет y = x^3 для данных x
        double[] yValuesCalculated = new double[xValues.length];
        for (int i = 0; i < xValues.length; i++) {
            yValuesCalculated[i] = Math.pow(xValues[i], 3); // Вычисление y = x^3 для каждого x
        }

        // 4. Вывод таблицы с вычисленными значениями y
        System.out.println("\nТаблица с вычисленными y = x^3:");
        printTable(xValues, yValuesCalculated); // Вывод таблицы с вычисленными y = x^3

        // 5. Интерполяция для вычисленных значений
        System.out.println("\nИнтерполяция для вычисленных y = x^3:");
        interpolate(xValues, yValuesCalculated, 27); // Интерполяция для значений x = 27 с вычисленными y
    }

    // Метод для печати таблицы
    public static void printTable(double[] xValues, double[] yValues) {
        System.out.println("x       y");
        for (int i = 0; i < xValues.length; i++) {
            // Форматированный вывод значений x и y в таблицу
            System.out.printf("%.2f   %.10f\n", xValues[i], yValues[i]);
        }
    }

    // Метод интерполяции
    public static void interpolate(double[] xValues, double[] yValues, double x) {
        int n = yValues.length; // Количество данных
        double[][] delta = new double[n][n]; // Массив для хранения конечных разностей

        // Заполняем начальные значения для конечных разностей
        System.arraycopy(yValues, 0, delta[0], 0, n);

        // Вычисляем конечные разности
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                delta[i][j] = delta[i - 1][j + 1] - delta[i - 1][j]; // Разности между соседними значениями
            }
        }

        // Печать таблицы конечных разностей
        System.out.println("x       y        Δy       Δ²y      Δ³y      Δ⁴y");
        for (int i = 0; i < n; i++) {
            System.out.printf("%.2f | %.10f", xValues[i], delta[0][i]); // Вывод значений для столбца y
            for (int j = 1; j < n - i; j++) {
                // Вывод конечных разностей для каждой степени
                System.out.printf(" | %.10f", delta[j][i]);
            }
            System.out.println();
        }

        // Интерполяция с использованием формулы Гаусса
        int mid = n - 1; // Начинаем с последнего элемента
        double q = (x - xValues[mid]) / h; // Расчет значения q для интерполяции
        double result = delta[0][mid]; // Начальное значение для результата
        double qProduct = 1;

        // Добавляем к результату члены разностного ряда
        for (int i = 1; i < n; i++) {
            qProduct *= (q + (i - 1)); // Умножаем q на (q + i - 1)
            double factorial = factorial(i); // Вычисляем факториал i
            int index = mid - i; // Индекс для выбора конечной разности
            result += (qProduct / factorial) * delta[i][index]; // Обновляем результат интерполяции
        }

        // Вывод интерполированного значения
        System.out.printf("Интерполированное значение при x = %.2f: %.10f\n", x, result);

        // Вычисление аналитического значения
        double analyticValue = Math.pow(x, 3); // Аналитическое значение для x^3
        System.out.printf("Аналитическое значение при x = %.2f: %.10f\n", x, analyticValue);

        // Погрешность
        double error = Math.abs(result - analyticValue); // Вычисление погрешности
        System.out.printf("Погрешность: %.10f\n", error);
    }

    // Метод для вычисления факториала числа
    public static double factorial(int n) {
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i; // Вычисление факториала
        }
        return result;
    }
}
