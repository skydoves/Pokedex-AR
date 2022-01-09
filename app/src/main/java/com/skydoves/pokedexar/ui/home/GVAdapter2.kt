package com.skydoves.pokedexar.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.database.BoxData
import com.skydoves.pokedexar.database.PokemonData

class GVAdapter2(val context: Context?) :BaseAdapter() {

    // 포켓몬 리스트를 멤버변수화.
    // 내 소유의 포켓몬 한 마리 = BoxData.

    var boxList: Array<BoxData> = emptyArray()

    private var mListener: OnItemClickListener? = null
    private var mListener1: OnLongItemClickListener? = null

    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }
    fun setOnLongItemClickListener(listener: OnLongItemClickListener?) {
        mListener1 = listener
    }

    override fun getCount(): Int {
        return boxList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val box = boxList[position]
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val pokeballView: View = inflater.inflate(R.layout.pokeball_item, parent, false);

        val pokemonPhoto = pokeballView.findViewById<ImageView>(R.id.pokeball)


        /* ArrayList<Dog>의 변수 dog의 이미지와 데이터를 ImageView와 TextView에 담는다. */
        val resourceId = context.resources.getIdentifier("pokemon${box.pokemon.id}", "drawable", context.packageName)
        pokemonPhoto.setImageResource(resourceId)

        pokemonPhoto.setOnLongClickListener{ p0 ->
            val pos = position
            mListener1?.onLongItemClick(p0, pos)

            true
        }


        pokemonPhoto.setOnClickListener{ p0 ->
            val pos = position
            mListener?.onItemClick(p0, pos)
        }

        return pokeballView
    }

    interface OnItemClickListener{
        fun onItemClick(v: View?, pos:Int)

    }

    interface OnLongItemClickListener{
        fun onLongItemClick(v: View?, pos: Int)
    }

}

