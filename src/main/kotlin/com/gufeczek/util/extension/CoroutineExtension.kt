package com.gufeczek.util.extension

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@OptIn(DelicateCoroutinesApi::class)
fun <T> lazyDeferred(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(dispatcher, start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}
