package com.desiredsoftware.socialquiz.ui.question

import android.os.Bundle
import android.util.Log
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

class QuestionShowingFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionShowingFragment()
    }

    private lateinit var viewModel: QuestionShowingViewModel

    lateinit var player : SimpleExoPlayer
    lateinit var playerControlView: StyledPlayerView
    lateinit var root : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_question_showing, container, false)

        val framePlayerLayout : AspectRatioFrameLayout = root.findViewById(R.id.framePlayerLayout)
        framePlayerLayout.setAspectRatio(16f/9f)

        val recyclerView : RecyclerView = root.findViewById(R.id.recyclerViewAnswerVariants)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),1)

        recyclerView.adapter = AnswerVariantsAdapter(generateQuestion().answerVariants)

        configurePlayer()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionShowingViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        configurePlayer()
        Log.d("QuestionShowingFragment", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("QuestionShowingFragment", "onStop")
        releasePlayer()
    }

    override fun onDestroyView() {
        Log.d("QuestionShowingFragment", "onDestroyView")

        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("QuestionShowingFragment", "onDestroy")

        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("QuestionShowingFragment", "onDetach")
        super.onDetach()
    }

    fun configurePlayer()
    {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        playerControlView = root.findViewById(R.id.playerView)
        playerControlView.player = player
        player.setMediaItem(MediaItem.fromUri(generateMediaURI()))
        player.prepare()
    }

    fun releasePlayer()
    {
        player.release()
    }
}