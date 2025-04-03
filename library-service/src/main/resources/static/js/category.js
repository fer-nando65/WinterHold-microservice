(() => {
  const url = "http://localhost:8080/api/category";
  const popUp = document.querySelector("#modal");
  const upsertPopUp = document.querySelector(".modal-content.modal-upsert");
  const tagCategoryName = document.querySelector("#modal #categoryName");
  const tagFloor = document.querySelector("#modal #floor");
  const tagIsle = document.querySelector("#modal #isle");
  const tagBay = document.querySelector("#modal #bay");
  const tagFormFlag = document.querySelector("#modal #formFlag");
  const submitButton = document.querySelector("#modal #submitModalUpsert");
  const closeButton = document.querySelector("#modal #closeModalUpsert");
  const addCategoryButton = document.querySelector("#addCategory");
  const editCategoryButton = document.querySelectorAll("a.edit");
  const modalHeader = document.querySelector("#modal .modal-upsert-info");

  // error tag
  const tagErrorCategory = document.querySelector(
    "#modal .validation-error.error-category"
  );
  const tagErrorFloor = document.querySelector(
    "#modal .validation-error.error-floor"
  );
  const tagErrorIsle = document.querySelector(
    "#modal .validation-error.error-isle"
  );

  const tagErrorBay = document.querySelector(
    "#modal .validation-error.error-bay"
  );

  let tagErrorMessageCategory = null,
    tagErrorMessageFloor = null,
    tagErrorMessageIsle = null,
    tagErrorMessageBay = null;

  addCategoryButton.addEventListener("click", () => {
    let categoryName = "-1";
    upsertCategory(categoryName);
  });

  for (let button of editCategoryButton) {
    button.addEventListener("click", () => {
      let categoryName = button.getAttribute("id");
      upsertCategory(categoryName);
    });
  }

  submitButton.addEventListener("click", () => {
    save();
  });

  closeButton.addEventListener("click", closePopUp);

  function closePopUp() {
    upsertPopUp.classList.toggle("active");
    popUp.classList.toggle("modal-active");
    modalHeader.innerHTML = "";
    tagCategoryName.removeAttribute("readonly");
    [tagErrorCategory, tagErrorFloor, tagErrorIsle, tagErrorBay].forEach(
      (tag) => tag.classList.remove("show-error")
    );
    [
      tagErrorMessageCategory,
      tagErrorMessageFloor,
      tagErrorMessageIsle,
      tagErrorMessageBay,
    ].forEach((tag) => (tag.innerHTML = ""));
  }

  function upsertCategory(categoryName) {
    if (categoryName === "-1") {
      modalHeader.textContent = "Add New Category";
    } else {
      modalHeader.textContent = `Edit ${categoryName} Category`;
      tagCategoryName.setAttribute("readonly", "");
    }

    let request = new XMLHttpRequest();
    request.open("GET", `${url}/upsert/${categoryName}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        let response = JSON.parse(request.response);
        tagCategoryName.value = response.categoryName;
        tagFloor.value = response.floor;
        tagIsle.value = response.isle;
        tagBay.value = response.bay;
        tagFormFlag.value = response.formFlag;
        upsertPopUp.classList.toggle("active");
        popUp.classList.toggle("modal-active");
      }
    };
  }

  function save() {
    let dto = {
      categoryName: tagCategoryName.value,
      floor: tagFloor.value,
      isle: tagIsle.value,
      bay: tagBay.value,
      formFlag: tagFormFlag.value,
    };

    let request = new XMLHttpRequest();
    request.open("POST", `${url}/save`);
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify(dto));
    request.onload = () => {
      if (request.status === 200) {
        window.location.reload();
      } else if (request.status === 422) {
        let response = JSON.parse(request.response);
        if (response.errorCategoryName != null) {
          tagErrorCategory.classList.add("show-error");
          if (tagErrorCategory) {
            tagErrorMessageCategory = tagErrorCategory.querySelector(".error");
            tagErrorMessageCategory.textContent = response.errorCategoryName;
          }
        }
        if (response.errorFloor != null) {
          tagErrorFloor.classList.add("show-error");
          if (tagErrorFloor) {
            tagErrorMessageFloor = tagErrorFloor.querySelector(".error");
            tagErrorMessageFloor.textContent = response.errorFloor;
          }
        } else {
          tagErrorFloor.classList.remove("show-error");
        }
        if (response.errorIsle != null) {
          tagErrorIsle.classList.add("show-error");
          if (tagErrorIsle) {
            tagErrorMessageIsle = tagErrorIsle.querySelector(".error");
            tagErrorMessageIsle.textContent = response.errorIsle;
          }
        } else {
          tagErrorIsle.classList.remove("show-error");
        }

        if (response.errorBay != null) {
          tagErrorBay.classList.add("show-error");
          if (tagErrorBay) {
            tagErrorMessageBay = tagErrorBay.querySelector(".error");
            tagErrorMessageBay.textContent = response.errorBay;
          }
        } else {
          tagErrorBay.classList.remove("show-error");
        }
      }
    };
  }

  //   Delete Section
  const deleteInfo = document.querySelector("#deleteInfo");
  const deletePopUpCategory = document.querySelector(
    ".modal-content.modal-delete"
  );
  const deleteButtonCategory = document.querySelectorAll("a.delete");
  const approveDeleteCategoryBtn = document.querySelector(
    "#modal #approveModalDelete"
  );
  const closeDeleteCategoryBtn = document.querySelector(
    "#modal #closeModalDelete"
  );

  for (let button of deleteButtonCategory) {
    if (!button.classList.contains("disabled")) {
      button.addEventListener("click", () => {
        let categoryName = button.getAttribute("id");
        approveDeleteCategoryBtn.setAttribute("data-id", categoryName);
        deleteInfo.textContent = `Are you sure want to delete ${categoryName} category ?`;
        popUp.classList.toggle("modal-active");
        deletePopUpCategory.classList.toggle("active");
      });
    }
  }

  approveDeleteCategoryBtn.addEventListener("click", () => {
    deleteCategory(approveDeleteCategoryBtn.getAttribute("data-id"));
  });

  closeDeleteCategoryBtn.addEventListener("click", closePopUpDelete);

  function closePopUpDelete() {
    deletePopUpCategory.classList.toggle("active");
    popUp.classList.toggle("modal-active");
    deleteInfo.innerHTML = "";
  }

  function deleteCategory(categoryName) {
    let request = new XMLHttpRequest();
    request.open("DELETE", `${url}/delete/${categoryName}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        window.location.reload();
      }
    };
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

  // loading script
  document.onreadystatechange = function () {
    if (document.readyState !== "complete") {
      document.querySelector("body").style.visibility = "hidden";
      document.querySelector("#loader").style.visibility = "visible";
    } else {
      document.querySelector("#loader").style.display = "none";
      document.querySelector("body").style.visibility = "visible";
    }
  };
})();
