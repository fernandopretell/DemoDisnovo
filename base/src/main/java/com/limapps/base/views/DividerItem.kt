package com.limapps.base.views

import com.limapps.base.R

class DividerItem(var color: Int = R.color.text_color_primary,
                  var thickness: Int = R.dimen.divider_height,
                  margin: Int = R.dimen.spacing_xlarge,
                  sides: Int = R.dimen.spacing_xlarge) {

    var marginTop = R.dimen.spacing_xlarge
    var marginBottom = R.dimen.spacing_xlarge
    var marginLeft = R.dimen.spacing_xlarge
    var marginRight = R.dimen.spacing_xlarge

    init {
        this.color = color
        this.thickness = thickness
        this.marginBottom = margin
        this.marginTop = margin
        this.marginLeft = sides
        this.marginRight = sides
    }
}