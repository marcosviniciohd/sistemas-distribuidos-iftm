package br.edu.iftm.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HadoopMapper
        extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable numeroUm = new IntWritable(1);
    private final Text palavra = new Text();
    private final String[] linguagens = {"java", "python", "c", "c++", "javascript", "ruby", "php", "swift", "go", "kotlin"};

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer tk = new StringTokenizer(value.toString());
        while (tk.hasMoreTokens()) {
            String token = tk.nextToken().toLowerCase();
            for (String linguagem : linguagens) {
                if (token.contains(linguagem)) {
                    palavra.set(linguagem);
                    context.write(palavra, numeroUm);
                    break;
                }
            }
        }
    }
}