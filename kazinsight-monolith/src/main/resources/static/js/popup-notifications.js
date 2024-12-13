popupNotifications = document.getElementsByClassName("popup-notification")

Array.from(popupNotifications).forEach(elem => {
    elem.style.top = "50px"
    setOpacityToZero = async () => {
        await new Promise(r => setTimeout(r, 3_000))
        elem.style.opacity = 0
    }
    setOpacityToZero()
})