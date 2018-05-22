package suanhang.jinan.com.suannihen.utils;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author MaTianyu
 * @date 2015-04-21
 */
public class HttpUtil {
    private static final String TAG = HttpUtil.class.getSimpleName();

    private static final String PATH_CPU = "/sys/devices/system/cpu/";
    private static final String CPU_FILTER = "cpu[0-9]+";
    private static int CPU_CORES = 0;

    public static String formatDate(long millis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(millis));
    }

    /**
     * Get available processors.
     */
    public static int getProcessorsCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or available processors if failed to get result
     */
    public static int getCoresNumbers() {
        if (CPU_CORES > 0) {
            return CPU_CORES;
        }
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                return Pattern.matches(CPU_FILTER, pathname.getName());
            }
        }
        try {
            //Get directory containing CPU info
            File dir = new File(PATH_CPU);
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            CPU_CORES = files.length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = Runtime.getRuntime().availableProcessors();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = 1;
        }
        return CPU_CORES;
    }
}
