package com.popstack.mvoter2015.feature.candidate.listing


//class ConstituentCandidateListViewModel @ViewModelInject constructor(
//  private val getCandidate: GetCandidateList,
//  private val globalExceptionHandler: GlobalExceptionHandler
//) : ViewModel() {
//
//  val viewItemLiveData = AsyncViewStateLiveData<CandidateListViewItem>()
//
//  fun loadCandidates(constituencyId: ConstituencyId, houseType: HouseType) {
//    viewModelScope.launch {
//      viewItemLiveData.postLoading()
//      kotlin.runCatching {
//        val candidateList = getCandidate.execute(GetCandidateList.Params(constituencyId))
//        val smallCandidateList = candidateList.map {
//          it.toSmallCandidateViewItem()
//        }
//        val candidateListViewItem = CandidateListViewItem(smallCandidateList)
//        viewItemLiveData.postSuccess(candidateListViewItem)
//      }.exceptionOrNull()?.let { exception ->
//        Timber.e(exception)
//        viewItemLiveData.postError(exception, globalExceptionHandler.getMessageForUser(exception))
//      }
//    }
//  }
//
//}