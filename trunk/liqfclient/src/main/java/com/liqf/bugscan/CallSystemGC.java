package com.liqf.bugscan;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;

/**
 * 
 * @author weique.lqf
 *
 */
public class CallSystemGC extends BytecodeScanningDetector {
    private BugReporter bugReporter;

    public CallSystemGC(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }
    
    public void sawOpcode(int seen) {
        if (seen == INVOKESTATIC) {
            if (getClassConstantOperand().equals("java/lang/System")
                    && getNameConstantOperand().equals("gc")) {
                bugReporter.reportBug(new BugInstance("SYSTEM_GC", NORMAL_PRIORITY)
                        .addClassAndMethod(this)
                        .addSourceLine(this));
            }
        }
    }
}
