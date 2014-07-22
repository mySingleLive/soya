package soya.env;

import java.io.IOException;

/**
 * SystemEnvironment is a singleton class, its methods will be called as global methods
 * @author: Jun Gong
 */
public class SystemEnvironment implements Environment {

    private static SystemEnvironment systemEnvironmentInstance = null;

    public static SystemEnvironment getSystemEnvironmentInstance() {
        if (systemEnvironmentInstance == null) {
            systemEnvironmentInstance = new SystemEnvironment();
        }
        return systemEnvironmentInstance;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Environment getParent() {
        return null;
    }

    @Override
    public void start() {
    }

    @Override
    public void end() {
    }


    /**
     * Terminates the currently running Java Virtual Machine. The
     * argument serves as a status code; by convention, a nonzero status
     * code indicates abnormal termination.
     * <p>
     * This method calls the <code>exit</code> method in class
     * <code>Runtime</code>. This method never returns normally.
     * <p>
     * The call <code>System.exit(n)</code> is effectively equivalent to
     * the call:
     * <blockquote><pre>
     * Runtime.getRuntime().exit(n)
     * </pre></blockquote>
     *
     * @param      status   exit status.
     * @throws  SecurityException
     *        if a security manager exists and its <code>checkExit</code>
     *        method doesn't allow exit with the specified status.
     * @see        java.lang.Runtime#exit(int)
     */
    public void exit(int status) {
        System.exit(status);
    }

    /**
     * Terminates the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     * @see        System#out#println()
     */
    public Object println() {
        System.out.println();
        return null;
    }

    /**
     * Prints an Object and then terminate the line.  This method calls
     * at first String.valueOf(x) to get the printed object's string value,
     * then behaves as
     * though it invokes <code>{@link System#out#print(String)}</code> and then
     * <code>{@link #println()}</code>.
     * @see        System#out#println(Object)
     *
     * @param obj  The <code>Object</code> to be printed.
     */
    public Object println(Object obj) {
        System.out.println(obj);
        return null;
    }

    /**
     * Prints an object.  The string produced by the <code>{@link
     * java.lang.String#valueOf(Object)}</code> method is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param      obj   The <code>Object</code> to be printed
     * @see        java.lang.Object#toString()
     */
    public Object print(Object obj) {
        System.out.print(obj);
        return null;
    }

    public void write(int b) {
        System.out.write(b);
    }

    public void write(byte[] b) throws IOException {
        System.out.write(b);
    }
}
