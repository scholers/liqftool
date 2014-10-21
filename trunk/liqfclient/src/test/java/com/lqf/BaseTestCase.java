package com.lqf;


public class BaseTestCase {
    /*

    private static Map<String, Script> scripts = new HashMap<String, Script>();

    @Test
    public void test() {
    	// fail("Not yet implemented");
    	long time1 = test_execute(false);
    	System.out
    			.println("------------------------------------------------------\n");
    	long time2 = test_execute(true);
    	System.out.println(time1 / time2);
    }

    public long test_execute(boolean isCached) {
    	// groovy脚本
    	String scriptStr = "def execute(Map temp){return [\"result\":\"hello world\" + temp.a]}";

    	// def code = new Source(source: script,type: "new",name: "hello")
    	Map<String, Object> context = new HashMap<String, Object>();

    	long start = System.currentTimeMillis();
    	for (int i = 0; i < 1000; i++) {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("a", "b" + i);
    		excute(context, params, scriptStr, isCached);
    	}
    	long time = System.currentTimeMillis() - start;
    	System.out.println("take time: " + time);
    	return time;

    }

    /**
     * 将脚本源码分析成Script对象
     * 
     * @param key
     *            将作为class name
     * @param scriptCode
     *            脚本源码
     * @return script对象
     */
    /*private Script parseScript(String[] args, String scriptCode, boolean isCached) {
        try {
            Script script = scripts.get("1");
            if (!isCached || script == null) {
                GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
                Class<?> scriptClass = groovyClassLoader.parseClass(scriptCode);
                Binding context = new Binding(args);
                script = InvokerHelper.createScript(scriptClass, context);
            }
            if (isCached) {
                scripts.put("1", script);
            }
            return script;
        } catch (Throwable e) {
            return null;
        }
    }

    private void excute(Map<String, Object> contentx, Map<String, Object> params, String code,
                        boolean isCached) {
        String[] args = new String[] { "aaa", "ddd" };
        Script script = parseScript(args, code, isCached);

        Map<String, Object> result = (Map<String, Object>) script.invokeMethod("execute", params);
        for (Map.Entry<String, Object> entry : result.entrySet()) {
        	System.out.println(entry.getValue());
        }
    }*/

}
