package bdtc.lab1;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Редьюсер: суммирует все единицы полученные от {@link HW1Mapper}, выдаёт суммарное значение нажатий по каждой области экрана
 */
public class HW1Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {

//     справочник диапазонов температур
    private Map<String, int[]> temperatureValues = new HashMap<String, int[]>() {{
        put("LOW", new int[] {0, 1000});
        put("MEDIUM", new int[] {1000, 10000});
        put("HIGH", new int[] {10000, -1});
    }};


    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        while (values.iterator().hasNext()) {
            sum += values.iterator().next().get();
        }
        context.write(new Text(key+" temperature- "+getTemperatureValue(sum)), new IntWritable(sum));
    }

    /**
     * функция определения температуры по количеству нажатий
     */
    protected String getTemperatureValue(int val) {
        String temperature = "";
        for (Map.Entry<String, int[]> entry: temperatureValues.entrySet()) {
            int[] value = entry.getValue();
            if ((!entry.getKey().equals("HIGH") && val >= value[0] && val < value[1])
                ||(entry.getKey().equals("HIGH") && val >= value[0]))
                temperature = entry.getKey();
        }
        return temperature;
    }
}
