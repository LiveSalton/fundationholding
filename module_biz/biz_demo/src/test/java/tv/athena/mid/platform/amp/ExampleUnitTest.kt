package tv.athena.mid.platform.amp

import org.junit.Test
import java.util.concurrent.LinkedBlockingQueue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        for (i in 0..100000) {
            val data = String.format("%06d", i)
            println(data)
        }
    }

    val mQueue: LinkedBlockingQueue<String> = LinkedBlockingQueue(150)
    var mIndex: Int = 0

    @Test
    fun pollAction() {
        //put
        println("put")
        for (i in 0..mIndex + 100) {
            val data = String.format("%06d", i)
            println(data)
            mQueue.add(data)
        }
        mIndex += 100

        println("poll")
        //take
        for (i in 0..50) {
            println(mQueue.poll())
        }
        //re put
        for (i in 0..mIndex + 100) {
            val data = String.format("%06d", i)
            println(data)
            mQueue.add(data)
        }
        mIndex += 100

    }
}
