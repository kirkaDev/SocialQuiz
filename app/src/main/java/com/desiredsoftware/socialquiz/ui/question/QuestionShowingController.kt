package com.desiredsoftware.socialquiz.ui.question

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.question.QuestionShowingPresenter
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class QuestionShowingController : MvpController(), QuestionShowingPresenter.IQuestionView {

    private lateinit var viewModel: QuestionShowingViewModel

    lateinit var player : SimpleExoPlayer
    lateinit var playerControlView: StyledPlayerView
    lateinit var root : View

    @Inject
    @InjectPresenter
    lateinit var presenter: QuestionShowingPresenter

    @ProvidePresenter
    fun provides() = presenter

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.view_controller_question_showing, container, false)
        val framePlayerLayout : AspectRatioFrameLayout = root.findViewById(R.id.framePlayerLayout)
        framePlayerLayout.setAspectRatio(16f/9f)

        viewModel = QuestionShowingViewModel()
        //viewModel.currentQuestion = args.question

                val recyclerView : RecyclerView = root.findViewById(R.id.recyclerViewAnswerVariants)
/*                recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
                recyclerView.adapter = AnswerVariantsAdapter(viewModel.currentQuestion.answerVariants,
                        object : OnClickAnswerListener{
                            override fun onClicked(answerVariant: Question.Answer) {
                                val answerIsCorrect : Boolean = answerVariant.isCorrect
                                *//*val action = QuestionShowingFragmentDirections.actionQuestionShowingFragmentToQuestionResultFragment(answerIsCorrect, viewModel.currentQuestion)
                                val navController = requireParentFragment().findNavController()
                                navController.navigate(action)*//*
                            }
                        })

                if (viewModel.currentQuestion.questionType.equals("video"))
                configurePlayer(viewModel.currentQuestion.questionBody)*/

                return root
            }

    fun configurePlayer(context : Context, videoURI : String )
    {
        player = SimpleExoPlayer.Builder(context).build()
        playerControlView = root.findViewById(R.id.playerView)
        playerControlView.player = player
        player.setMediaItem(MediaItem.fromUri(videoURI))
        player.prepare()
    }

    override fun showQuestionBody() {
        TODO("Not yet implemented")
    }

    override fun showAnswerVariants() {
        TODO("Not yet implemented")
    }
}