class ParamsFactory {

    sort;
    sortSide = 'DESC';

    get() {
        let line = '';
        if (!this.isEmpty()) {
            line = '?';
        }
        if (this.sort || this.sort != "") {
            line = line + 'sort_field=' + this.sort + '&sort_side=' + this.sortSide;
        }
        return line;
    }

    isEmpty() {
        if (this.sort || this.sortSide) {
            return false;
        }
        return true;
    }

    addSort(sort) {
        if (sort === this.sort && this.sortSide === 'DESC') {
            this.sortSide = 'ASC';
        } else {
            this.sort = sort;
            this.sortSide = 'DESC';
        }
    }

}
