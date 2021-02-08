package array_max_value;

public class ArrayMaxValue
{
    public static int getMaxValue(int[] values) throws Exception {
        int maxValue = Integer.MIN_VALUE;

        if (values == null || values.length == 0){ //добавил условие при котором values может быть null
            throw new Exception();
        }


        for(int value : values)
        {
            if (value > maxValue) {
                maxValue = value;
            }
        }


        return maxValue;
    }
}