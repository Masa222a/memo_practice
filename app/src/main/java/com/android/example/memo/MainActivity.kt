package com.android.example.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MemoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MemoListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.memo_list)
        recyclerView.adapter = adapter

        val editText = findViewById<EditText>(R.id.memo_edit_text)
        val addButton = findViewById<Button>(R.id.add_button)

        val realm = Realm.getDefaultInstance()

        addButton.setOnClickListener {
            val text = editText.text.toString()
            if (text.isEmpty()) {
                return@setOnClickListener
            }

            realm.executeTransactionAsync {
                val memo = it.createObject(Memo::class.java)
                memo.name = text
                it.copyFromRealm(memo)
            }

            editText.text.clear()
        }

        //DBに変更があったときに通知が来る
        realm.addChangeListener {
            //リストをアップデート
            val memoList = it.where(Memo::class.java).findAll().map { it.name }
            //UIを更新
            recyclerView.post {
                adapter.updateMemoList(memoList)
            }
        }

        //初回表示にリストを表示
        realm.executeTransactionAsync {
            val memoList = it.where(Memo::class.java).findAll().map { it.name }
            //UIを更新
            recyclerView.post {
                adapter.updateMemoList(memoList)
            }
        }
    }
}