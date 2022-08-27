package com.android.example.memo

import io.realm.RealmObject

open class Memo(
    open var name: String = ""
) : RealmObject()