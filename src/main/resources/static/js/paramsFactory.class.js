class ParamsFactory {

    sort;
    sortSide = 'DESC';
    limit = 10;
    offset = 0;

    get() {
        let and = false;
        let line = '';

        if (!this.isEmpty()) {
            line = '?';
        }
        if (this.sort && this.sort != "") {
            line = line + 'sort_field=' + this.sort + '&sort_side=' + this.sortSide;
            and = true;
        }
        if (this.limit) {
            const stringAnd = and?'&':'';
            line = line + stringAnd +'limit=' + this.limit + '&offset=' + this.offset;
            and = true;
        }
        return line;
    }

    isEmpty() {
        if ((this.sort && this.sort != "") || this.limit) {
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
