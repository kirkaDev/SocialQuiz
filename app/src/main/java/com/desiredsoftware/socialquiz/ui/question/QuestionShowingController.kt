package com.desiredsoftware.socialquiz.ui.question

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.question.QuestionShowingPresenter
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController.Companion.CATEGORY_ID_KEY
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.ui.components.AnswersAdapter
import com.desiredsoftware.socialquiz.ui.components.OnClickAnswerListener
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class QuestionShowingController : MvpController, QuestionShowingPresenter.IQuestionView {
    constructor() : super()
    constructor (args: Bundle) : super(args)

    private var player: SimpleExoPlayer? = null
    private lateinit var playerControlView: PlayerView
    lateinit var rootView: View

    lateinit var answerVariants: RecyclerView
    lateinit var mAdapter: AnswersAdapter

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
        rootView = inflater.inflate(R.layout.view_controller_question_showing, container, false)

        answerVariants = rootView.findViewById(R.id.listAnswers)
        playerControlView = rootView.findViewById(R.id.playerView)

        presenter.questionCategoryId = args.getString(CATEGORY_ID_KEY).toString()

        presenter.initUI()

        return rootView
    }

    private fun configurePlayer(context: Context, videoURI: String) {
        player = SimpleExoPlayer.Builder(context)
            .build()

        playerControlView.player = player
        player?.let {
            it.setMediaItem(MediaItem.fromUri(videoURI))
            it.prepare()
            it.playWhenReady = true

            it.addListener(object: Player.Listener{
                override fun onPlaybackStateChanged(state: Int) {
                    when (state) {
                        Player.STATE_ENDED -> {
                            answerVariants.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }

        playerControlView.hideController()
    }

    override fun showVideoQuestion(context: Context, questionBodyVideoUri: String) {
        configurePlayer(context, questionBodyVideoUri)
    }

    override fun showTextQuestion(context: Context, questionBodyVideoUri: String) {
        // TODO("Not yet implemented")
    }

    override fun popCurrentController() {
        router.popCurrentController()
    }

    override fun showAnswers(context: Context, answers: List<Answer>) {
        mAdapter = AnswersAdapter(answersList = answers, object : OnClickAnswerListener {
            override fun onClicked(answer: Answer) {
                val bundle = Bundle()
                bundle.putSerializable(ANSWER_IS_CORRECT_KEY, answer.isCorrect)
                val transaction = RouterTransaction.with(QuestionResultController(bundle))
                router.pushController(transaction)
            }
        })

        answerVariants.adapter = mAdapter
        answerVariants.layoutManager = LinearLayoutManager(context)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        player?.pause()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        if (player != null) {
            player?.stop()
            player?.release()
            player = null
        }
    }

    companion object {
        val ANSWER_IS_CORRECT_KEY = "ANSWER_IS_CORRECT_KEY"
    }
}