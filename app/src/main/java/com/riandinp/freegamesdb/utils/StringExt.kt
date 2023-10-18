package com.riandinp.freegamesdb.utils

fun getPublisherDeveloper(publisher: String, developer: String): String {
    return if (publisher == developer) publisher
    else "${publisher}/${developer}"
}