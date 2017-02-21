# Get Waiting For List Of Current User
getMatchedListData = () ->
  $.ajax "/matches/getmatchedlist",
    type: 'GET'
    dataType: 'json'
    success: (data, textStatus, jqXHR) ->
      htmlTarget = $("#matchediterator")
      title = "<h1>Matched Renters</h1>"
      listBody = ""
      $.each data, (index, user) ->
        listCell = "<div class='col-sm-" + "6" + " text-center'>" +
        "<figure> " +
        "<img class=' mx-auto d-block img-rounded img-responsive img-fluid' src='/assets/images/temp.png' >" +
        "<figcaption>" + user.fullname + "</figcaption> " +
        "</figure></div>"
        listBody += listCell
      if listBody.length is 0 then listBody = """<strong>No results</strong>"""
      htmlTarget.html(title + listBody)
      $("#matchediterator").trigger("update", true)
    error: (jqXHR, textStatus, errorThrown) ->
      window.location.href = "/";
  setTimeout getMatchedListData, 10000
$ ->
  getMatchedListData()

# Get Waiting For List Of Current User
getWaitingListData = () ->
  $.ajax "/matches/getwaitinglist",
    type: 'GET'
    dataType: 'json'
    success: (data, textStatus, jqXHR) ->
      htmlTarget = $("#waitingiterator tbody")
      tableBody = ""
      $.each data, (index, user) ->
        tableRow = "<tr><td>" + user.email + "</td>" +
        "<td>" + user.fullname + "</td>"+
        "<td><button type='button' class='btn btn-info btn-xs'  title='Cancle Request' userid='" + user.id + "'><i class='icon-trash'></i> <strong>" + "Cancle" + "</strong></button>" + "</td>" +
        "</tr>"
        tableBody += tableRow
      if tableBody.length is 0 then tableBody = """<tr><td colspan="2"><strong>No results</strong></td></tr>"""
      htmlTarget.html(tableBody)
      $("#waitingiterator").trigger("update", true)
    error: (jqXHR, textStatus, errorThrown) ->
      window.location.href = "/";
  setTimeout getWaitingListData, 10000
$ ->
  getWaitingListData()

# Get New Suggestion List Of Current User
getNewSuggestionListData = () ->
  $.ajax "/matches/getnewsuggestionlist",
    type: 'GET'
    dataType: 'json'
    success: (data, textStatus, jqXHR) ->
      htmlTarget = $("#newSuggestionIterator")
      title = "<h1>New Suggestions</h1>"
      listBody = ""
      $.each data, (index, user) ->
        listCell = "<div class='col-sm-" + "2" + " text-center'>" +
        "<figure> " +
        "<img class=' mx-auto d-block img-rounded img-responsive img-fluid descrption-tooltip' src='/assets/images/temp.png' title='description' data-toggle='tooltip' data-placement='bottom'>" +
        "<figcaption>" + user.fullname + "</figcaption> " +
        "<figcaption><button type='button' class='btn btn-danger btn-xs' title='description' userid='" + user.id + "'><i class='icon-like'></i> <strong>"+ "Like" +"</strong></button>&nbsp;&nbsp;<button type='button' class='btn btn-success btn-xs' title='description' userid='" + user.id + "'><i class='icon-unlike'></i> <strong>" + "UnLike" + "</strong></button></figcaption> " +
        "</figure></div>"
        listBody += listCell
      if listBody.length is 0 then listBody = """<strong>No results</strong>"""
      htmlTarget.html(title + listBody)
      $("#newSuggestionIterator").trigger("update", true)
    error: (jqXHR, textStatus, errorThrown) ->
      window.location.href = "/";
  setTimeout getNewSuggestionListData, 10000
$ ->
  getNewSuggestionListData()


