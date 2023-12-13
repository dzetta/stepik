package rihilke.stepik

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SecondActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        val vEdit=findViewById<EditText>(R.id.act_2_text)

        findViewById<Button>(R.id.act_2_btn_text).setOnClickListener {
            val new_str = vEdit.text.toString()
            val i = Intent()
            //почему тэг2
            i.putExtra("tag2",new_str)
            //что такое резултКод
            setResult(0,i)
            // просто закрываем этот экран
            finish()
        }

        //берем текст с первого экрана
        val str = intent.getStringExtra("tag1")
        vEdit.setText(str)
    }
}