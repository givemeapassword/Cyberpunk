package com.example.rp_cyberpunk_list

import android.content.ContentResolver
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_cyberpunk_list.databinding.CardItemBinding
import com.example.rp_cyberpunk_list.db.MySQLManager
import java.io.InputStream


class CharacterAdapter(private val listener: Listener, val context: Context):
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val characterList = ArrayList<Characters>()

    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = CardItemBinding.bind(itemView)
        fun bind(characters: Characters, listener: Listener){
            binding.apply {
                if (characters.image.contains("/")){
                    cardImage.setImageURI(characters.image.toUri())
                }
                else{
                    cardImage.setImageResource(characters.image.toInt())
                }
                charactersName.text = characters.name
                charactersClass.text = characters.role

                cardCharacter.setOnClickListener{
                    listener.onClick(characters)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        return holder.bind(characterList[position],listener)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun addCharacter(characters: Characters){
        characterList.add(characters)
        notifyDataSetChanged()
    }

    fun addCards(cardData: ArrayList<Characters>){
        characterList.clear()
        characterList.addAll(cardData)
        notifyDataSetChanged()
    }

    fun removeCard(position: Int,dbManager: MySQLManager){
        dbManager.deleteFromDb(characterList[position].id)
        characterList.removeAt(position)
        notifyItemRangeChanged(0,characterList.size)
        notifyItemRemoved(position)
    }


    interface Listener{
        fun onClick(characters: Characters)
    }
}
