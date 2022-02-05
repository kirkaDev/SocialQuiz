package com.desiredsoftware.socialquiz.ui.question

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class QuestionShowingController : MvpController, QuestionShowingPresenter.IQuestionView {
    constructor() : super()
    constructor (args: Bundle) : super(args)

    lateinit var mPlayer: SimpleExoPlayer
    lateinit var mPlayerControlView: StyledPlayerView
    lateinit var mRoot: View

    lateinit var mListAnswers: RecyclerView
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
        mRoot = inflater.inflate(R.layout.view_controller_question_showing, container, false)

        mListAnswers = mRoot.findViewById(R.id.listAnswers)

        val framePlayerLayout: AspectRatioFrameLayout = mRoot.findViewById(R.id.framePlayerLayout)
        framePlayerLayout.setAspectRatio(16f / 9f)

        presenter.mQuestionCategoryId = args.getString(CATEGORY_ID_KEY).toString()
        presenter.showQuestion()

        return mRoot
    }

    private fun configurePlayer(context: Context, videoURI: String) {
        mPlayer = SimpleExoPlayer.Builder(context).build()
        mPlayerControlView = mRoot.findViewById(R.id.playerView)
        mPlayerControlView.player = mPlayer
        mPlayer.setMediaItem(MediaItem.fromUri(videoURI))
        mPlayer.prepare()
    }

    override fun showVideoQuestion(context: Context, questionBodyVideoUri: String) {
        configurePlayer(context, questionBodyVideoUri)
    }

    override fun showAnswers(context: Context, answers: List<Answer>) {
        mAdapter = AnswersAdapter(answersList = answers, object: OnClickAnswerListener{
            override fun onClicked(answer: Answer) {
                val bundle = Bundle()
                bundle.putSerializable(ANSWER_IS_CORRECT_KEY, answer.isCorrect)
                val transaction = RouterTransaction.with(QuestionResultController(bundle))
                router.pushController(transaction)
            }
        })

        mListAnswers.adapter = mAdapter
        mListAnswers.layoutManager = LinearLayoutManager(context)
    }

    companion object{
        val ANSWER_IS_CORRECT_KEY = "ANSWER_IS_CORRECT_KEY"
    }
}