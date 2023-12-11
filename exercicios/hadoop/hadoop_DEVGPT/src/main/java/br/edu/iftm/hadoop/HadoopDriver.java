package br.edu.iftm.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HadoopDriver {

    public static void main(String[] args)
            throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "conta hashtags");

        job.setJarByClass(HadoopDriver.class);
        job.setMapperClass(HadoopMapper.class);
        job.setReducerClass(HadoopReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("gpt"));
        FileOutputFormat.setOutputPath(job, new Path("gptout"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}