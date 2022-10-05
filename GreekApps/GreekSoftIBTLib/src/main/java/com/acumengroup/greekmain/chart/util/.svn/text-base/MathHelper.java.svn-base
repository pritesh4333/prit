package com.acumengroup.greekmain.chart.util;

import java.util.List;

/**
 * * Created by Arcadia
 */
public class MathHelper {

    public static double closestValue(double nearValue, List<Double> values) {
        double min = Integer.MAX_VALUE;
        double closest = nearValue;

        for (Double v : values) {
            final double diff = Math.abs(v - nearValue);
            if (diff < min) {
                min = diff;
                closest = v;
            }
        }
        return closest;
    }

    public static double preNearestValue(double nearValue, List<Double> values) {
        double nearestValue = closestValue(nearValue, values);
        if (nearestValue > nearValue) {
            int index = values.indexOf(nearestValue);
            if (index != 0) index--;
            nearestValue = values.get(index);
        }
        return nearestValue;
    }

    public static double postNearestValue(double nearValue, List<Double> values) {
        double nearestValue = closestValue(nearValue, values);
        if (nearestValue < nearValue) {
            int index = values.indexOf(nearestValue);
            if (index != (values.size() - 1)) index++;
            nearestValue = values.get(index);
        }
        return nearestValue;
    }

    public static float closestValue(float nearValue, List<Float> values) {
        float min = Integer.MAX_VALUE;
        float closest = nearValue;

        for (Float v : values) {
            final float diff = Math.abs(v - nearValue);
            if (diff < min) {
                min = diff;
                closest = v;
            }
        }

        return closest;
    }

    public static float preNearestValue(float nearValue, List<Float> values) {
        float nearestValue = closestValue(nearValue, values);
        if (nearestValue > nearValue) {
            int index = values.indexOf(nearestValue);
            if (index != 0) index--;
            nearestValue = values.get(index);
        }
        return nearestValue;
    }

    public static float postNearestValue(float nearValue, List<Float> values) {
        float nearestValue = closestValue(nearValue, values);
        if (nearestValue < nearValue) {
            int index = values.indexOf(nearestValue);
            if (index != (values.size() - 1)) index++;
            nearestValue = values.get(index);
        }
        return nearestValue;
    }

    public static double[] getMinMaxFromSelectedValue(List<double[]> values, int startPoint, int endPoint) {

        // 2 for min & max
        double minMax[] = new double[values.size() * 2];
        int k = 0;
        for (int tot = 0; tot < values.size(); tot++, k += 2) {
            double minValue = values.get(tot)[startPoint];
            double maxValue = values.get(tot)[startPoint];
            for (int i = startPoint + 1; i < endPoint; i++) {
                if (values.get(tot)[i] < minValue) {
                    minValue = values.get(tot)[i];
                }
                if (values.get(tot)[i] > maxValue) {
                    maxValue = values.get(tot)[i];
                }
            }
            minMax[k] = minValue;
            minMax[k + 1] = maxValue;
        }
        return getMinMaxValue(minMax);
    }

    /**
     * This method return minimum value by picking even numbers in order
     *
     * @param numbers
     * @return
     */
    public static float getMinValueFromEvenOrder(float[] numbers) {
        float minValue = numbers[0];
        for (int i = 1; i < numbers.length; i += 2) {
            if (numbers[i] < minValue) {
                minValue = numbers[i];
            }
        }
        return minValue;
    }

    public static double[] getMinMaxValue(double[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return new double[2];
        }
        double minValue = numbers[0];
        double maxValue = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < minValue) {
                minValue = numbers[i];
            }
            if (numbers[i] > maxValue) {
                maxValue = numbers[i];
            }
        }
        return new double[]{minValue, maxValue};
    }

    public static int[] getGraphPoints(double minValue, double maxValue) {
        int[] finalValue = new int[2];
        int valueToBeAdded = 0;

        int divisor = (int) (maxValue - minValue);

        int valueToBeSubtracted = 0;
        if (divisor < 100) {
            if (minValue % 10 == 0) {
                valueToBeSubtracted = (minValue > 0) ? 10 : -10;
            } else {
                valueToBeSubtracted = (minValue > 0) ? 1 : -1;
            }

            if (maxValue % 10 == 0) {
                valueToBeAdded = (maxValue > 0) ? 10 : -10;
            } else {
                valueToBeAdded = (maxValue > 0) ? 1 : -1;
            }

        } else {

            int len = Integer.toString(divisor).length();

            int power = (int) Math.pow(10, len - 2);

            power = (0 == power) ? 1 : (1 == power) ? 10 : power;

            valueToBeSubtracted = (int) ((minValue < 0) ? ((minValue * -1) % power) : (minValue % power));

            int max = (int) (maxValue % power);

            if (valueToBeSubtracted == 0) {
                valueToBeSubtracted = power;
            }

            if (minValue < 0) {
                valueToBeSubtracted = ((valueToBeSubtracted != 10) ? (power - valueToBeSubtracted) : (valueToBeSubtracted)) * -1;
            }

            if (maxValue < 0) {
                valueToBeAdded = max;
            } else {
                valueToBeAdded = power - max;
            }


        }

        finalValue[0] = (int) ((minValue > 0) ? (minValue - valueToBeSubtracted) : (minValue + valueToBeSubtracted));
        finalValue[1] = (int) ((maxValue > 0) ? (maxValue + valueToBeAdded) : (maxValue - valueToBeAdded));

        return finalValue;
    }

}
