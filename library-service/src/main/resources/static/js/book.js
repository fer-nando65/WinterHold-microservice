(() => {
  const url = "http://localhost:8080/api/book";
  const popUp = document.querySelector("#modal");
  const summaryPopUp = document.querySelector(".modal-content.modal-summary");
  const summaryInfo = document.querySelector("#summaryInfo");
  const openSummaryButton = document.querySelectorAll("a.summary");
  const closeSummaryButton = document.querySelector("#closeModalSummary");

  for (let button of openSummaryButton) {
    if (!button.classList.contains("disabled")) {
      button.addEventListener("click", () => {
        let code = button.getAttribute("id");
        showSummary(code);
      });
    }
  }

  closeSummaryButton.addEventListener("click", closePopUpSummary);

  function showSummary(code) {
    let request = new XMLHttpRequest();
    request.open("GET", `${url}/summary/${code}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        summaryInfo.textContent = request.response;
        summaryPopUp.classList.toggle("active");
        popUp.classList.toggle("modal-active");
      }
    };
  }

  function closePopUpSummary() {
    popUp.classList.toggle("modal-active");
    summaryPopUp.classList.toggle("active");
    summaryInfo.innerHTML = "";
  }

  //   Delete Section
  const deletePopUp = document.querySelector(".modal-content.modal-delete");
  const openDeleteButton = document.querySelectorAll("a.delete");
  const approveDeleteButton = document.querySelector("#approveModalDelete");
  const closeDeleteButton = document.querySelector("#closeModalDelete");
  const deleteInfo = document.querySelector("#deleteInfo");

  for (let button of openDeleteButton) {
    if (!button.classList.contains("disabled")) {
      button.addEventListener("click", () => {
        let code = button.getAttribute("id");
        popUpDelete(code);
      });
    }
  }

  closeDeleteButton.addEventListener("click", () => {
    closePopUpDelete();
  });

  function popUpDelete(code) {
    let request = new XMLHttpRequest();
    request.open("GET", `${url}/title/${code}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        approveDeleteButton.setAttribute("data-id", code);
        deleteInfo.textContent = `Are you sure want to delete "${request.response}" book ?`;
        deletePopUp.classList.toggle("active");
        popUp.classList.toggle("modal-active");
      }
    };
  }

  approveDeleteButton.addEventListener("click", () => {
    deleteBook(approveDeleteButton.getAttribute("data-id"));
  });

  function deleteBook(code) {
    let request = new XMLHttpRequest();
    request.open("DELETE", `${url}/delete/${code}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        window.location.reload();
      }
    };
  }

  function closePopUpDelete() {
    popUp.classList.toggle("modal-active");
    deletePopUp.classList.toggle("active");
    deleteInfo.innerHTML = "";
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
