package com.hearoad.hearoad.ui.adapter

import com.hearoad.hearoad.databinding.ItemSoundBinding
//import com.hearoad.hearoad.ui.viewmodel.SoundItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hearoad.hearoad.data.model.response.EmergencyVoiceResponse

class SoundAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<SoundAdapter.SoundViewHolder>() {

    private var currentlyPlayingPosition: Int = -1
    private val soundItems = mutableListOf<EmergencyVoiceResponse>()

    inner class SoundViewHolder(private val binding: ItemSoundBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(soundItem: EmergencyVoiceResponse) {
            binding.tvSubtitle.text = soundItem.word
            binding.tvItemsound.text = soundItem.emoji

            binding.root.setOnClickListener {
                onItemClick(soundItem.filePath)
                updatePlayingIndicator(position)
            }
            binding.ivItemsoundIndicator.visibility = if (currentlyPlayingPosition == position) View.VISIBLE else View.GONE
        }
        private fun updatePlayingIndicator(newPosition: Int) {
            val previousPosition = currentlyPlayingPosition
            currentlyPlayingPosition = newPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(currentlyPlayingPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
        val binding = ItemSoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SoundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
        holder.bind(soundItems[position])
    }

    override fun getItemCount(): Int = soundItems.size

    fun submitList(items: List<EmergencyVoiceResponse>) {
        soundItems.clear()
        soundItems.addAll(items)
        notifyDataSetChanged()
    }
}
