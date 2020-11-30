package com.desiredsoftware.socialquiz.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.ui.components.AnswerVariantsAdapter
import com.desiredsoftware.socialquiz.utils.generateMediaURI
import com.desiredsoftware.socialquiz.utils.generateQuestion
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView

class QuestionShowingFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionShowingFragment()
    }

    private lateinit var viewModel: QuestionShowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_question_showing, container, false)

        val recyclerView : RecyclerView = root.findViewById(R.id.recyclerViewAnswerVariants)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),1)

        recyclerView.adapter = AnswerVariantsAdapter(generateQuestion().answerVariants)



        val playerControlView: StyledPlayerView = root.findViewById(R.id.playerView)
        var player : SimpleExoPlayer  = SimpleExoPlayer.Builder(requireContext()).build()
        playerControlView.player = player

        player.setMediaItem(MediaItem.fromUri(generateMediaURI()))
        player.prepare()
        player.play()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionShowingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}