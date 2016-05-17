package net.gy.SwiftFrameWork.Reactive.test;

import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.OnPublisher;
import net.gy.SwiftFrameWork.Reactive.annotation.RunContext;
import net.gy.SwiftFrameWork.Reactive.entity.RunContextType;
import net.gy.SwiftFrameWork.Reactive.impl.Observer;
import net.gy.SwiftFrameWork.Reactive.impl.Publisher;

/**
 * Created by pc on 16/5/14.
 */
public class Test {
    private void test(){
        Publisher.<String>getInstance()
                 .create(new OnPublisher<String>() {
                     @RunContext(RunContextType.Calculate)
                     @Override
                     public void call(OnObserver<String> observer) {
                     }
                 })
                 .with(new String[]{})
                 .bind(new Observer<String>() {
                     @RunContext(RunContextType.MainThread)
                     @Override
                     public void onSuccess(String s) {

                     }

                     @Override
                     public void onError(Throwable throwable) {

                     }

                     @Override
                     public void onFinished() {

                     }
                 });
    }
}
