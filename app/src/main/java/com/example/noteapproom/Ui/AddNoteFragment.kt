package com.example.noteapproom.Ui


import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.noteapproom.Db.Note
import com.example.noteapproom.Db.NoteDatabase

import com.example.noteapproom.R
import com.example.noteapproom.Util.showToast
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

/**
 * A simple [Fragment] subclass.
 */
class AddNoteFragment : Fragment() {
private var note:Note?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {

            note=AddNoteFragmentArgs.fromBundle(it).note
            edit_text_note.setText(note?.note)
            edit_text_title.setText(note?.title)
        }


    button_save.setOnClickListener{view->

        val title=edit_text_title.text.toString().trim()
        val notes=edit_text_note.text.toString().trim()


        if(title.isEmpty()){
            edit_text_title.error="Title Required"
            edit_text_title.requestFocus()
            return@setOnClickListener
        }

        if (notes.isEmpty()){
            edit_text_note.error="Note Required please write something"
            edit_text_note.requestFocus()
            return@setOnClickListener
        }

         // saveNote(mynote)

             CoroutineScope(IO).launch {
                 var job=Job()

                 var mynote=Note(title,notes)


                 var add= NoteDatabase.invoke(activity!!).getNoteDao()
                 withContext(Main){
                     job?.let {
                         if(note==null){
                             add.addNote(mynote)
                             activity!!.showToast("successfully saved")
                         }
                         else{
                             mynote.id=note!!.id
                             add.updateNote(mynote)
                             activity!!.showToast("updated successfully")
                         }

                         //Toast.makeText(activity,"Saved Successfully",Toast.LENGTH_LONG).show()

                         var action=AddNoteFragmentDirections.actionSaveNote()
                          Navigation.findNavController(view).navigate(action)

                     }
                     job?.complete()
                 }


             }



    }




    }

    override fun onDestroy() {
        super.onDestroy()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete->if (note!=null) deleteNote() else activity!!.showToast("Can not delte")
        }
        return super.onOptionsItemSelected(item)

    }

    private fun deleteNote() {

        AlertDialog.Builder(activity).apply {
            setTitle("Are you sure ?")
            setMessage("you can note undo this operation")
            setPositiveButton("YES"){_,_->
                CoroutineScope(IO).launch {
                    NoteDatabase(activity!!).getNoteDao().deletenote(note!!)
                    withContext(Main){
                        edit_text_title.setText("")
                        edit_text_note.setText(" ")
                       activity!!.showToast("Deleted successfully")
                   }
                }
        }
            setNegativeButton("NO"){_,_->

            }


        }.create().show()

    }

}
