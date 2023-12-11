package br.edu.iftm.hadoop;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HadoopReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable resultado = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
        int soma = 0;
        for (IntWritable val : values) {
            soma += val.get();
        }
        resultado.set(soma);
        context.write(key, resultado);
    }
    
}