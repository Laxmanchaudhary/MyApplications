package com.example.noteapproom.Ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapproom.Adapter.MyNoteAdapter
import com.example.noteapproom.Db.NoteDatabase

import com.example.noteapproom.R
import kotlinx.android.synthetic.main.fragment_home_.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 */
class Home_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Recycler_View_Notes.setHasFixedSize(true)
        Recycler_View_Notes.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        CoroutineScope(IO).launch {


                val notes=NoteDatabase.invoke(activity!!).getNoteDao().getAllNote()
            withContext(Main){
                Recycler_View_Notes.adapter=MyNoteAdapter(notes,activity!!)

            }
        }



        button_add.setOnClickListener({
            val action=Home_FragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        })
    }
}
