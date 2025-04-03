(() => {
  const url = "http://localhost:8080/api/loan";
  const popUp = document.querySelector("#modal");

  // upsert
  const upsertPopUp = document.querySelector(".modal-content.modal-upsert");
  const selectOptionCustomer = document.querySelector("#customerOption");
  const selectOptionBook = document.querySelector("#bookOption");
  const tagLoanDate = document.querySelector("#loanDate");
  const tagLoanNote = document.querySelector("#loanNote");
  const tagLoanId = document.querySelector("#loanId");
  const btnSubmitLoan = document.querySelector("#submitModalUpsert");
  const btnCloseLoan = document.querySelector("#closeModalUpsert");
  const btnNewLoan = document.querySelector(".add-button");
  const btnEditLoan = document.querySelectorAll(".button.edit");
  const upsertInfo = document.querySelector(".modal-upsert-info");

  // error upsert
  const tagErrorCustomer = document.querySelector(
    ".validation-error.modal-error.error-customer"
  );
  const errorCustomer = tagErrorCustomer.querySelector(".error");

  const tagErrorBook = document.querySelector(
    ".validation-error.modal-error.error-book"
  );
  const errorBook = tagErrorBook.querySelector(".error");

  const tagErrorLoanDate = document.querySelector(
    ".validation-error.modal-error.error-date"
  );
  const errorLoanDate = tagErrorLoanDate.querySelector(".error");

  btnNewLoan.addEventListener("click", () => {
    upsert(0);
  });

  for (let button of btnEditLoan) {
    if (!button.classList.contains("disabled")) {
      button.addEventListener("click", () => {
        let id = button.getAttribute("id");
        upsert(id);
      });
    }
  }

  function upsert(id) {
    if (id === 0) {
      upsertInfo.textContent = "Add New Loan";
    } else {
      upsertInfo.textContent = "Edit Loan";
    }
    let request = new XMLHttpRequest();
    request.open("GET", `${url}/upsert/${id}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        let response = JSON.parse(request.response);
        tagLoanId.value = response.id;
        tagLoanDate.value = response.loanDate;
        tagLoanNote.value = response.note;

        addPlaceHolderOption(selectOptionCustomer, "select customer");
        addPlaceHolderOption(selectOptionBook, "select book");

        for (let opt of response.listCustomer) {
          let newOption = document.createElement("option");
          newOption.textContent = opt.text;
          newOption.setAttribute("value", opt.value);
          if (opt.selected) {
            newOption.selected = true;
          }
          selectOptionCustomer.appendChild(newOption);
        }

        for (let opt of response.listBook) {
          let newOption = document.createElement("option");
          newOption.textContent = opt.text;
          newOption.setAttribute("value", opt.value);
          if (opt.selected) {
            newOption.selected = true;
          }
          selectOptionBook.appendChild(newOption);
        }

        popUp.classList.toggle("active");
        upsertPopUp.classList.toggle("modal-active");
      }
    };
  }

  btnCloseLoan.addEventListener("click", closeUpsert);

  function closeUpsert() {
    popUp.classList.toggle("active");
    upsertPopUp.classList.toggle("modal-active");
    tagLoanId.removeAttribute("value");
    tagLoanDate.removeAttribute("value");
    tagLoanNote.removeAttribute("value");
    selectOptionCustomer.innerHTML = "";
    selectOptionBook.innerHTML = "";

    [tagErrorCustomer, tagErrorBook, tagErrorLoanDate].forEach((tag) =>
      tag.classList.remove("show-error")
    );

    [errorCustomer, errorBook, errorLoanDate].forEach(
      (tag) => (tag.innerHTML = "")
    );
  }

  btnSubmitLoan.addEventListener("click", () => {
    dto = {
      id: null,
      customerId: "",
      bookCode: "",
      loanDate: null,
      note: "",
    };

    dto.id = tagLoanId.value;
    dto.customerId =
      selectOptionCustomer.value === "select customer"
        ? ""
        : selectOptionCustomer.value;
    dto.bookCode =
      selectOptionBook.value === "select book" ? "" : selectOptionBook.value;
    dto.loanDate = tagLoanDate.value;
    dto.note = tagLoanNote.value;
    submitLoan(dto);
  });

  function submitLoan(dto) {
    let request = new XMLHttpRequest();
    request.open("POST", `${url}/save`);
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify(dto));
    request.onload = () => {
      if (request.status === 200) {
        window.location.reload();
      } else if (request.status === 422) {
        let response = JSON.parse(request.response);
        if (response.errorCustomer != null) {
          errorCustomer.textContent = response.errorCustomer;
          tagErrorCustomer.classList.add("show-error");
        } else {
          tagErrorCustomer.classList.remove("show-error");
        }

        if (response.errorBook != null) {
          errorBook.textContent = response.errorBook;
          tagErrorBook.classList.add("show-error");
        } else {
          tagErrorBook.classList.remove("show-error");
        }

        if (response.errorLoanDate != null) {
          errorLoanDate.textContent = response.errorLoanDate;
          tagErrorLoanDate.classList.add("show-error");
        } else {
          tagErrorLoanDate.classList.remove("show-error");
        }
      }
    };
  }

  function addPlaceHolderOption(tagSelect, text) {
    let option = document.createElement("option");
    option.disabled = true;
    option.hidden = true;
    option.selected = true;
    option.textContent = text;
    tagSelect.appendChild(option);
  }

  // return section
  const returnPopUp = document.querySelector(".modal-content.modal-return");
  const returnInfo = document.querySelector("#returnInfo");
  const btnApproveReturn = document.querySelector("#approveModalReturn");
  const btnCloseReturn = document.querySelector("#closeModalReturn");
  const btnReturn = document.querySelectorAll(".button.return");

  for (let button of btnReturn) {
    if (!button.classList.contains("disabled")) {
      button.addEventListener("click", () => {
        let id = button.getAttribute("id");
        showReturnPopUp(id);
      });
    }
  }

  function showReturnPopUp(id) {
    let request = new XMLHttpRequest();
    request.open("GET", `${url}/get/book/title/${id}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        btnApproveReturn.setAttribute("data-id", id);
        returnInfo.textContent = `Are you sure want to return "${request.response}" book ?`;
        popUp.classList.toggle("active");
        returnPopUp.classList.toggle("modal-active");
      }
    };
  }

  btnApproveReturn.addEventListener("click", () => {
    let id = btnApproveReturn.getAttribute("data-id");
    returnLoan(id);
  });

  function returnLoan(id) {
    let request = new XMLHttpRequest();
    request.open("PUT", `${url}/return/${id}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        window.location.reload();
      }
    };
  }

  btnCloseReturn.addEventListener("click", closeReturnPopUp);

  function closeReturnPopUp() {
    popUp.classList.toggle("active");
    returnPopUp.classList.toggle("modal-active");
    returnInfo.innerHTML = "";
  }

  // detail section
  const detailPopUp = document.querySelector(".modal-content.modal-detail");
  const tagTitleDetail = detailPopUp.querySelector("#titleDetail");
  const tagCategoryDetail = detailPopUp.querySelector("#categoryNameDetail");
  const tagAuthorDetail = detailPopUp.querySelector("#authorDetail");
  const tagFloorDetail = detailPopUp.querySelector("#floorDetail");
  const tagIsleDetail = detailPopUp.querySelector("#isleDetail");
  const tagBayDetail = detailPopUp.querySelector("#bayDetail");
  const tagMembershipDetail = detailPopUp.querySelector(
    "#membershipNumberDetail"
  );
  const tagFullNameDetail = detailPopUp.querySelector("#fullNameDetail");
  const tagPhoneDetail = detailPopUp.querySelector("#phoneDetail");
  const tagMembershipNumEx = detailPopUp.querySelector("#membershipNumEx");
  const btnOpenLoanDetail = document.querySelectorAll(".button.detail");
  const btnCloseLoanDetail = document.querySelector("#closeModalDetail");
  const btnXCloseLoanDetail = document.querySelector("#closeModal");

  for (let button of btnOpenLoanDetail) {
    button.addEventListener("click", () => {
      let id = button.getAttribute("id");
      showDetail(id);
    });
  }

  function showDetail(id) {
    let request = new XMLHttpRequest();
    request.open("GET", `${url}/detail/${id}`);
    request.send();
    request.onload = () => {
      let response = JSON.parse(request.response);
      if (response.status === 200) {
        let data = response.data;
        tagTitleDetail.textContent = data.title;
        tagCategoryDetail.textContent = data.categoryName;
        tagAuthorDetail.textContent = data.authorName;
        tagFloorDetail.textContent = data.floor;
        tagIsleDetail.textContent = data.isle;
        tagBayDetail.textContent = data.bay;
        tagMembershipDetail.textContent = data.membershipNumber;
        tagFullNameDetail.textContent = data.fullName;
        tagPhoneDetail.textContent = data.phoneNumber;
        tagMembershipNumEx.textContent = data.membershipExpiredDate;

        detailPopUp.classList.toggle("modal-active");
        popUp.classList.toggle("active");
      }
    };
  }

  [btnCloseLoanDetail, btnXCloseLoanDetail].forEach((tag) =>
    tag.addEventListener("click", closeLoanDetail)
  );

  function closeLoanDetail() {
    [
      tagTitleDetail,
      tagCategoryDetail,
      tagAuthorDetail,
      tagFloorDetail,
      tagIsleDetail,
      tagBayDetail,
      tagMembershipDetail,
      tagFullNameDetail,
      tagPhoneDetail,
      tagMembershipNumEx,
    ].forEach((tag) => (tag.innerHTML = ""));

    popUp.classList.toggle("active");
    detailPopUp.classList.toggle("modal-active");
  }

  //   No Scroll While Refresh Section
  history.scrollRestoration = "manual";
  window.addEventListener("beforeunload", function () {
    localStorage.setItem("scrollPos", window.scrollY);
  });

  window.addEventListener("load", function () {
    const scrollPos = localStorage.getItem("scrollPos");
    if (scrollPos) {
      window.scrollTo(0, parseInt(scrollPos, 10));
    }
  });
})();
