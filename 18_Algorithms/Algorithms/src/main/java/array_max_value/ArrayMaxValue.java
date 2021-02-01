package array_max_value;

public class ArrayMaxValue
{
    public static int getMaxValue(int[] values) throws Exception
    {
        int maxValue = Integer.MIN_VALUE;
        try {
            if (values == null && values.length == 0){ //добавил условие при котором values может быть null
            }
        } catch (Exception ex){
            System.out.println(ex);
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