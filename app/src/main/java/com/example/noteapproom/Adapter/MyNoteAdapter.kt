package com.example.noteapproom.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapproom.Db.Note
import com.example.noteapproom.R
import com.example.noteapproom.Ui.Home_FragmentDirections
import kotlinx.android.synthetic.main.note_layout.view.*

class MyNoteAdapter(var notelist:List<Note>,var context:Context):RecyclerView.Adapter<MyNoteAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view=LayoutInflater.from(context).inflate(R.layout.note_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return notelist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.textview_note.text=notelist[position].note
        holder.itemView.textview_title.text=notelist[position].title
        holder.itemView.setOnClickListener{
               var action=Home_FragmentDirections.actionAddNote()
              action.note=notelist[position]
              Navigation.findNavController(it).navigate(action)



        }

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}