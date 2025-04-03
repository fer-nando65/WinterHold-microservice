(() => {
  const url = "http://localhost:8080/api/author";
  const deleteButton = document.querySelectorAll("a.delete");
  const popUp = document.querySelector("#modal");
  const deletePopUp = document.querySelector(".modal-content.modal-delete");
  const approveDelete = document.querySelector("#approveModalDetele");
  const closeDelete = document.querySelector("#closeModalDelete");
  const deleteInfo = document.querySelector("#deleteInfo");

  for (let button of deleteButton) {
    if (!button.classList.contains("disabled")) {
      button.addEventListener("click", () => {
        let id = button.getAttribute("id");
        let requestName = new XMLHttpRequest();
        requestName.open("GET", `${url}/name/${id}`);
        requestName.send();
        requestName.onload = () => {
          if (requestName.status === 200) {
            approveDelete.setAttribute("data-id", id);
            deleteInfo.textContent = `Are you sure want to delete author ${requestName.response} ?`;
            deletePopUp.classList.toggle("active");
            popUp.classList.toggle("modal-active");
          }
        };
      });
    }
  }

  approveDelete.addEventListener("click", () => {
    deleteAuthor(approveDelete.getAttribute("data-id"));
  });

  function deleteAuthor(id) {
    let request = new XMLHttpRequest();
    request.open("DELETE", `${url}/delete/${id}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        window.location.reload();
      }
    };
  }

  closeDelete.addEventListener("click", closeModal);

  function closeModal() {
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
