package es.flakiness.hello.stringbench.stringbenchmarks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    static class BenchmarkParams {
        final int iterations;
        final int intValue;
        final float floatValue;
        final String stringValue;

        public BenchmarkParams(int iterations, int intValue, float floatValue, String stringValue) {
            this.iterations = iterations;
            this.intValue = intValue;
            this.floatValue = floatValue;
            this.stringValue = stringValue;
        }
    }

    class BenchmarkTask extends AsyncTask<BenchmarkParams, Void, String> {


        private long measure(Runnable runnable) {
            System.gc();
            long start = SystemClock.currentThreadTimeMillis();
            runnable.run();
            long end = SystemClock.currentThreadTimeMillis();
            return end - start;
        }

        private void unuse(String s) {

        }

        @Override
        protected String doInBackground(BenchmarkParams... paramsList) {
            final BenchmarkParams params = paramsList[0];

            long formatTime = measure(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < params.iterations; ++i) {
                        unuse(String.format("%d%s%f", params.intValue, params.stringValue, params.floatValue));
                    }
                }
            });

            long concatTime = measure(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < params.iterations; ++i) {
                        unuse(params.intValue + params.stringValue + params.floatValue);
                    }
                }
            });

            long buildTime = measure(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < params.iterations; ++i) {
                        StringBuilder builder = new StringBuilder();
                        builder.append(params.intValue);
                        builder.append(params.stringValue);
                        builder.append(params.floatValue);
                        unuse(builder.toString());
                    }
                }
            });

            long buildWithCapacityTime = measure(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < params.iterations; ++i) {
                        StringBuilder builder = new StringBuilder(100);
                        builder.append(params.intValue);
                        builder.append(params.stringValue);
                        builder.append(params.floatValue);
                        unuse(builder.toString());
                    }
                }
            });

            return String.format("format: %dms\nconcat: %dms\nbuild: %dms\nbuildWithCapacity:%dms",
                    formatTime, concatTime, buildTime, buildWithCapacityTime);
        }

        @Override
        protected void onPostExecute(String result) {
            ((TextView)findViewById(R.id.result_text)).setText(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBenchmark();
            }
        });
    }

    private void startBenchmark() {
        new BenchmarkTask().execute(
                new BenchmarkParams(
                        10000,
                        1234567890,
                        0.123456789f,
                        "1234567890"));
    }
}