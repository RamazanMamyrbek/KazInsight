reviewStars = document.getElementsByClassName("review-star")

let WHITE_STAR_CLASS = "white-star"
let YELLOW_STAR_CLASS = "yellow-star"
let SOLID_STAR = "fa-star"
let HALF_STAR = "fa-star-half-stroke"

colorStars(reviewStars, 1)

function colorStars(stars, rating) {
    for (let i = 0; i < rating; i++) {
        stars[i].classList.remove(WHITE_STAR_CLASS)
        stars[i].classList.add(YELLOW_STAR_CLASS)
        if (i + 1 > rating) {
            stars[i].classList.remove(SOLID_STAR)
            stars[i].classList.add(HALF_STAR)
        }
    }

    for (let i = Math.ceil(rating); i < stars.length; i++) {
        stars[i].classList.remove(YELLOW_STAR_CLASS)
        stars[i].classList.add(WHITE_STAR_CLASS)
    }
}

Array.from(reviewStars).forEach((element, idx) => {
    element.addEventListener('click', () => {
        colorStars(reviewStars, idx + 1)
        document.getElementById("rating-value").value = idx + 1
    })
});