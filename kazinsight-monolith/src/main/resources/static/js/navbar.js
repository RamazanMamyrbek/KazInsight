locationContainer = document.getElementById("location-text")
citiesContainer = document.getElementById("cities-container")

locationContainer.addEventListener("mouseenter", (event) => {
    citiesContainer.style.display = "block"
})

citiesContainer.addEventListener("mouseenter", (event) => {
    citiesContainer.style.display = "block"
})

citiesContainer.addEventListener("mouseleave", (event) => {
    citiesContainer.style.display = "none"
})

locationContainer.addEventListener("mouseleave", (event) => {
    citiesContainer = document.getElementById("cities-container")
    citiesContainer.style.display = "none"
})

userIconContainer = document.getElementById('user-icon-img')
userNavPopupContainer = document.getElementById('user-nav-popup')

userIconContainer.addEventListener("mouseenter", (event) => {
    userNavPopupContainer.style.display = "block"
})

userNavPopupContainer.addEventListener("mouseenter", (event) => {
    userNavPopupContainer.style.display = "block"
})

userIconContainer.addEventListener("mouseleave", (event) => {
    userNavPopupContainer.style.display = "none"
})

userNavPopupContainer.addEventListener("mouseleave", (event) => {
    userNavPopupContainer.style.display = "none"
})