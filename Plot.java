import StdDraw.java;
public class Plot {
   public Plot() {
      int n = Integer.parseInt();
      double[] x = new double[n+1];
      double[] y = new double[n+1];
      for (int i = 0; i <= n; i++) {
         x[i] = i
         y[i] = 0;
         for (int i = 0; i < coefficientList.size(); i++) {
            y[i] += coefficientList.get(i) * i^exponentList.get(i);
         }
      }
      for (int i = 0; i < n; i++) {
         StdDraw.line(x[i], y[i], x[i+1], y[i+1]);
      }
   }
}