package org.cocos2d.actions.instant;

import android.util.Log;
import org.cocos2d.nodes.CocosNode;

/**
 * Calls a 'callback' with the node as the first argument and the 2nd argument is data
 * ND means: Node Data
 */
public class CallFuncND extends CallFuncN {
    protected Object data;

    public static CallFuncND action(Object t, String s, Object d) {
        return new CallFuncND(t, s, d);
    }

    /**
     * creates the action with the callback and the data to pass as an argument
     */
    protected CallFuncND(Object t, String s, Object d) {
        init (t, s);
        data = d;

        try {
            Class cls = targetCallback.getClass();
            Class partypes[] = new Class[2];
            partypes[0] = CocosNode.class;
            partypes[1] = Object.class;
            invocation = cls.getMethod(selector, partypes);
        } catch (Exception e) {
			Log.e("CallFuncND", "Exception " + e.toString());
        }
    }

	@Override
    public CallFuncND copy() {
        return new CallFuncND(targetCallback, selector, data);
    }

    /**
     * executes the callback
     */
    public void execute() {
        try {
            invocation.invoke(targetCallback, target, data);
        } catch (Exception e) {
        }
    }
}
