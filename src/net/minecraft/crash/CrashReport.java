package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.optifine.CrashReporter;
import net.optifine.reflect.Reflector;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sertyo.events.Main;

public class CrashReport
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final String description;
    private final Throwable cause;
    private final CrashReportCategory systemDetailsCategory = new CrashReportCategory(this, "Доп информация");
    private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
    private File crashReportFile;
    private boolean firstCategoryInCrashReport = true;
    private StackTraceElement[] stacktrace = new StackTraceElement[0];
    private boolean reported = false;

    public CrashReport(String descriptionIn, Throwable causeThrowable)
    {
        this.description = descriptionIn;
        this.cause = causeThrowable;
        this.populateEnvironment();
    }

    /**
     * Populates this crash report with initial information about the running server and operating system / java
     * environment
     */
    private void populateEnvironment()
    {
        this.systemDetailsCategory.addDetail("Версия майнкрафта", () ->
        {
            return SharedConstants.getVersion().getName();
        });

        this.systemDetailsCategory.addDetail("Ос", () ->
        {
            return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
        });
        this.systemDetailsCategory.addDetail("Версия джавы", () ->
        {
            return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
        });
        this.systemDetailsCategory.addDetail("Версия вм джавы", () ->
        {
            return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
        });
        this.systemDetailsCategory.addDetail("Памчти", () ->
        {
            Runtime runtime = Runtime.getRuntime();
            long i = runtime.maxMemory();
            long j = runtime.totalMemory();
            long k = runtime.freeMemory();
            long l = i / 1024L / 1024L;
            long i1 = j / 1024L / 1024L;
            long j1 = k / 1024L / 1024L;
            return k + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
        });


        if (Reflector.CrashReportExtender_enhanceCrashReport != null)
        {
            Reflector.CrashReportExtender_enhanceCrashReport.call(this, this.systemDetailsCategory);
        }
    }

    /**
     * Returns the description of the Crash Report.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Returns the Throwable object that is the cause for the crash and Crash Report.
     */
    public Throwable getCrashCause()
    {
        return this.cause;
    }

    /**
     * Gets the various sections of the crash report into the given StringBuilder
     */
    public void getSectionsInStringBuilder(StringBuilder builder)
    {
        if ((this.stacktrace == null || this.stacktrace.length <= 0) && !this.crashReportSections.isEmpty())
        {
            this.stacktrace = ArrayUtils.subarray(this.crashReportSections.get(0).getStackTrace(), 0, 1);
        }

        if (this.stacktrace != null && this.stacktrace.length > 0)
        {
            builder.append("-- Head --\n");
            builder.append("Thread: ").append(Thread.currentThread().getName()).append("\n");

            if (Reflector.CrashReportExtender_generateEnhancedStackTraceSTE.exists())
            {
                builder.append("Stacktrace:");
                builder.append(Reflector.CrashReportExtender_generateEnhancedStackTraceSTE.callString1(this.stacktrace));
            }
            else
            {
                builder.append("Stacktrace:\n");

                for (StackTraceElement stacktraceelement : this.stacktrace)
                {
                    builder.append("\t").append("at ").append((Object)stacktraceelement);
                    builder.append("\n");
                }

                builder.append("\n");
            }
        }

        for (CrashReportCategory crashreportcategory : this.crashReportSections)
        {
            crashreportcategory.appendToStringBuilder(builder);
            builder.append("\n\n");
        }

        this.systemDetailsCategory.appendToStringBuilder(builder);
    }

    /**
     * Gets the stack trace of the Throwable that caused this crash report, or if that fails, the cause .toString().
     */
    public String getCauseStackTraceOrString()
    {
        StringWriter stringwriter = null;
        PrintWriter printwriter = null;
        Throwable throwable = this.cause;

        if (throwable.getMessage() == null)
        {
            if (throwable instanceof NullPointerException)
            {
                throwable = new NullPointerException(this.description);
            }
            else if (throwable instanceof StackOverflowError)
            {
                throwable = new StackOverflowError(this.description);
            }
            else if (throwable instanceof OutOfMemoryError)
            {
                throwable = new OutOfMemoryError(this.description);
            }

            throwable.setStackTrace(this.cause.getStackTrace());
        }

        if (Reflector.CrashReportExtender_generateEnhancedStackTraceT.exists())
        {
            return Reflector.CrashReportExtender_generateEnhancedStackTraceT.callString(throwable);
        }
        else
        {
            String s;

            try
            {
                stringwriter = new StringWriter();
                printwriter = new PrintWriter(stringwriter);
                throwable.printStackTrace(printwriter);
                s = stringwriter.toString();
            }
            finally
            {
                IOUtils.closeQuietly((Writer)stringwriter);
                IOUtils.closeQuietly((Writer)printwriter);
            }

            return s;
        }
    }

    /**
     * Gets the complete report with headers, stack trace, and different sections as a string.
     */
    public String getCompleteReport()
    {
        if (!this.reported)
        {
            this.reported = true;
            CrashReporter.onCrashReport(this, this.systemDetailsCategory);
        }

        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- Neiron crash info ----\n");

        if (Reflector.CrashReportExtender_addCrashReportHeader != null && Reflector.CrashReportExtender_addCrashReportHeader.exists())
        {
            Reflector.CrashReportExtender_addCrashReportHeader.call(stringbuilder, this);
        }

        stringbuilder.append("// ");
        stringbuilder.append(getWittyComment());
        stringbuilder.append("\n\n");
        stringbuilder.append("Время краша: ");
        stringbuilder.append((new SimpleDateFormat()).format(new Date()));
        stringbuilder.append("\n");
        stringbuilder.append("Причина: ");
        stringbuilder.append(this.description);
        stringbuilder.append("\n\n");
        stringbuilder.append(this.getCauseStackTraceOrString());
        stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

        for (int i = 0; i < 87; ++i)
        {
            stringbuilder.append("-");
        }

        stringbuilder.append("\n\n");
        this.getSectionsInStringBuilder(stringbuilder);
        return stringbuilder.toString();
    }

    /**
     * Gets the file this crash report is saved into.
     */
    public File getFile()
    {
        return this.crashReportFile;
    }

    /**
     * Saves this CrashReport to the given file and returns a value indicating whether we were successful at doing so.
     */
    public boolean saveToFile(File toFile)
    {
        if (this.crashReportFile != null)
        {
            return false;
        }
        else
        {
            if (toFile.getParentFile() != null)
            {
                toFile.getParentFile().mkdirs();
            }

            Writer writer = null;
            boolean flag;

            try
            {
                writer = new OutputStreamWriter(new FileOutputStream(toFile), StandardCharsets.UTF_8);
                writer.write(this.getCompleteReport());
                this.crashReportFile = toFile;
                return true;
            }
            catch (Throwable throwable)
            {
                LOGGER.error("Could not save crash report to {}", toFile, throwable);
                flag = false;
            }
            finally
            {
                IOUtils.closeQuietly(writer);
            }

            return flag;
        }
    }

    public CrashReportCategory getCategory()
    {
        return this.systemDetailsCategory;
    }

    /**
     * Creates a CrashReportCategory
     */
    public CrashReportCategory makeCategory(String name)
    {
        return this.makeCategoryDepth(name, 1);
    }

    /**
     * Creates a CrashReportCategory for the given stack trace depth
     */
    public CrashReportCategory makeCategoryDepth(String categoryName, int stacktraceLength)
    {
        CrashReportCategory crashreportcategory = new CrashReportCategory(this, categoryName);

        try
        {
            if (this.firstCategoryInCrashReport)
            {
                int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
                StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
                StackTraceElement stacktraceelement = null;
                StackTraceElement stacktraceelement1 = null;
                int j = astacktraceelement.length - i;

                if (j < 0)
                {
                    System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
                }

                if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length)
                {
                    stacktraceelement = astacktraceelement[j];

                    if (astacktraceelement.length + 1 - i < astacktraceelement.length)
                    {
                        stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
                    }
                }

                this.firstCategoryInCrashReport = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement, stacktraceelement1);

                if (i > 0 && !this.crashReportSections.isEmpty())
                {
                    CrashReportCategory crashreportcategory1 = this.crashReportSections.get(this.crashReportSections.size() - 1);
                    crashreportcategory1.trimStackTraceEntriesFromBottom(i);
                }
                else if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j && j < astacktraceelement.length)
                {
                    this.stacktrace = new StackTraceElement[j];
                    System.arraycopy(astacktraceelement, 0, this.stacktrace, 0, this.stacktrace.length);
                }
                else
                {
                    this.firstCategoryInCrashReport = false;
                }
            }
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

        this.crashReportSections.add(crashreportcategory);
        return crashreportcategory;
    }

    /**
     * Gets a random witty comment for inclusion in this CrashReport
     */
    private static String getWittyComment()
    {

        try
        {
            return "Neiron has been crashed:(";
        }
        catch (Throwable throwable)
        {
            return "Witty comment unavailable :(";
        }
    }

    /**
     * Creates a crash report for the exception
     */
    public static CrashReport makeCrashReport(Throwable causeIn, String descriptionIn)
    {
        while (causeIn instanceof CompletionException && causeIn.getCause() != null)
        {
            causeIn = causeIn.getCause();
        }

        CrashReport crashreport;

        if (causeIn instanceof ReportedException)
        {
            crashreport = ((ReportedException)causeIn).getCrashReport();
        }
        else
        {
            crashreport = new CrashReport(descriptionIn, causeIn);
        }

        return crashreport;
    }

    public static void crash()
    {
        (new CrashReport("Don't panic!", new Throwable())).getCompleteReport();
    }
}
